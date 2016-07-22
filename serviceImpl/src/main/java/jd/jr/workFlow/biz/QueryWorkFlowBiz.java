package jd.jr.workFlow.biz;

import jd.jr.workFlow.bo.request.QueryRequestBo;
import jd.jr.workFlow.bo.response.QueryResponseBo;
import jd.jr.workFlow.bo.response.TaskResultBo;
import jd.jr.workFlow.dao.ProcessInstancesEntityMapper;
import jd.jr.workFlow.dao.impl.ProcessInstancesEntityDao;
import jd.jr.workFlow.enums.ResponseCodeEnum;
import jd.jr.workFlow.model.ProcessInstancesEntity;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/7.
 */
@Component
public class QueryWorkFlowBiz {


    private final static Logger LOG = Logger.getLogger(QueryWorkFlowBiz.class);

    @Autowired
    private ProcessInstancesEntityDao processInstancesEntityDao;

    @Resource(name = "processInstancesEntityMapper")
    private ProcessInstancesEntityMapper processInstancesEntityMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;


    /**
     * 查询待办业务处理类
     *
     * @param queryRequestBo Service 层传入的参数
     * @return QueryRequestBo 待办列表
     */
    public QueryResponseBo queryToDo(QueryRequestBo queryRequestBo) {
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        queryResponseBo.setRespCode(ResponseCodeEnum.Success.getCode());
        queryResponseBo.setRespMessage(ResponseCodeEnum.Success.getMessage());
        LOG.info("用户：" + queryRequestBo.getUserID() + "正在查询待办，所属角色组：" + queryRequestBo.getRoleGroupID());
        ArrayList<TaskResultBo> taskResultBoList = new ArrayList<TaskResultBo>();
        List<Task> taskList = new ArrayList<Task>();
        if (!StringUtils.isEmpty(queryRequestBo.getRoleGroupID())) {
            LOG.info("查询角色组的待办：" + queryRequestBo.getRoleGroupID());
            taskList.addAll(taskService.createTaskQuery().taskCandidateGroup(queryRequestBo.getRoleGroupID()).taskTenantId(queryRequestBo.getSystemSourceID()).list());
        }
        if (!StringUtils.isEmpty(queryRequestBo.getUserID())) {
            LOG.info("查询指定用户的待办列表：" + queryRequestBo.getUserID());
            taskList.addAll(taskService.createTaskQuery().taskAssignee(queryRequestBo.getUserID()).taskTenantId(queryRequestBo.getSystemSourceID()).list());
        }
        for (Task task : taskList) {
            TaskResultBo taskResult = new TaskResultBo();
            taskResult.setTaskID(task.getId());
            taskResult.setDescription(task.getDescription());
            taskResult.setTaskName(task.getName());
            taskResult.setProcessInstanceId(task.getProcessInstanceId());
            Map variables = taskService.getVariables(task.getId());
            taskResult.setOpinions((String) variables.get("opinions"));
            taskResult.setProcessInstanceIniter((String) variables.get("initer"));
            taskResult.setRemark((String) variables.get("remark"));
            //查询业务单号并反馈
            List<ProcessInstancesEntity> instancesL = processInstancesEntityDao.selectBizByInstance(queryRequestBo.getSystemSourceID(), queryRequestBo.getBizType(), task.getProcessInstanceId());
            if (!CollectionUtils.isEmpty(instancesL)) {
                taskResult.setBussinessID(instancesL.get(0).getBizID());
            }
            taskResultBoList.add(taskResult);
        }
        queryResponseBo.setTaskResultList(taskResultBoList);
        return queryResponseBo;
    }


    /**
     * 查询用户已办
     *
     * @param queryRequestBo
     * @return QueryRequestBo
     */
    public QueryResponseBo queryHadDone(QueryRequestBo queryRequestBo) {
        LOG.info("用户：" + queryRequestBo.getUserID() + "正在查询已办");
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        queryResponseBo.setRespCode(ResponseCodeEnum.Success.getCode());
        queryResponseBo.setRespMessage(ResponseCodeEnum.Success.getMessage());
        ArrayList<TaskResultBo> taskResultBoList = new ArrayList<TaskResultBo>();
        // TODO: 2016/7/18 分页和条数限制
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .taskAssignee(queryRequestBo.getUserID()).activityTenantId(queryRequestBo.getSystemSourceID()).finished()
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().desc()
                // 执行查询
                .list();
        for (HistoricActivityInstance historicActivityInstance : hais) {
            TaskResultBo taskResultBo = new TaskResultBo();
            taskResultBo.setProcessInstanceId(historicActivityInstance.getProcessInstanceId());
            taskResultBo.setTaskName(historicActivityInstance.getActivityName());
            taskResultBo.setCreateTime(historicActivityInstance.getStartTime());
            taskResultBo.setEndTime(historicActivityInstance.getEndTime());
            //查询业务单号并反馈
            List<ProcessInstancesEntity> instancesL = processInstancesEntityDao.selectBizByInstance(queryRequestBo.getSystemSourceID(), queryRequestBo.getBizType(), historicActivityInstance.getProcessInstanceId());
            if (!CollectionUtils.isEmpty(instancesL)) {
                taskResultBo.setBussinessID(instancesL.get(0).getBizID());
            }
            taskResultBoList.add(taskResultBo);
        }
        queryResponseBo.setTaskResultList(taskResultBoList);
        return queryResponseBo;
    }

    /**
     * 查询历程的所有活动节点
     *
     * @param queryRequestBo
     * @return QueryRequestBo 活动节点集合
     */
    public QueryResponseBo queryProcessIntenceDetail(QueryRequestBo queryRequestBo) {
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        queryResponseBo.setRespCode(ResponseCodeEnum.Success.getCode());
        queryResponseBo.setRespMessage(ResponseCodeEnum.Success.getMessage());
        LOG.info("查询流程的所有节点：" + queryRequestBo.toString());
        LOG.info("按照业务单号查询流程实例ID");
        String processInstanceID = "";
        ProcessInstancesEntity param = new ProcessInstancesEntity();
        param.setSystemSourceId(queryRequestBo.getSystemSourceID());
        param.setBizType(queryRequestBo.getBizType());
        param.setBizID(queryRequestBo.getBussinessID());
        List<ProcessInstancesEntity> instanceList = processInstancesEntityMapper.selectByParams(param);
        if (CollectionUtils.isEmpty(instanceList)) {
            LOG.info("不存在" + queryRequestBo.getBussinessID() + "对应的流程ID");
            queryResponseBo.setRespCode(ResponseCodeEnum.Error_100003.getCode());
            queryResponseBo.setRespMessage(ResponseCodeEnum.Error_100003.getMessage());
            return queryResponseBo;
        } else {
            processInstanceID = instanceList.get(0).getProcessIntanceId();
        }
        LOG.info("流程实例ID:" + processInstanceID);
        ArrayList<TaskResultBo> taskResultBoList = new ArrayList<TaskResultBo>();
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .processInstanceId(processInstanceID)
                .activityTenantId(queryRequestBo.getSystemSourceID())
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().desc()
                // 执行查询
                .list();// TODO: 2016/7/18 添加分页
        for (HistoricActivityInstance hai : hais) {
            TaskResultBo task = new TaskResultBo();
            task.setTaskName(hai.getActivityName());
            task.setUserID(hai.getAssignee());
            task.setEndTime(hai.getEndTime());
            task.setDuration(hai.getDurationInMillis() == null ? 0 : hai.getDurationInMillis());
            //查询业务单号并反馈
            List<ProcessInstancesEntity> instancesL = processInstancesEntityDao.selectBizByInstance(queryRequestBo.getSystemSourceID(), queryRequestBo.getBizType(), processInstanceID);
            if (!CollectionUtils.isEmpty(instancesL)) {
                task.setBussinessID(instancesL.get(0).getBizID());
            }
            //不记录网关节点
            if (!"exclusiveGateway".equals(hai.getActivityType())) {
                taskResultBoList.add(task);
            }
        }
        queryResponseBo.setTaskResultList(taskResultBoList);

        return queryResponseBo;
    }
}