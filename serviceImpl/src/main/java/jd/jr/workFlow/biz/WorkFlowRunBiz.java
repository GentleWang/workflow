package jd.jr.workFlow.biz;

import jd.jr.workFlow.bo.request.OperateRequestBo;
import jd.jr.workFlow.bo.request.StartProcessRequestBo;
import jd.jr.workFlow.dao.ProcessDefinitionEntityMapper;
import jd.jr.workFlow.dao.ProcessInstancesEntityMapper;
import jd.jr.workFlow.enums.ProcessInstanceStatusEnum;
import jd.jr.workFlow.enums.ResponseCodeEnum;
import jd.jr.workFlow.model.ProcessDefinitionEntity;
import jd.jr.workFlow.model.ProcessInstancesEntity;
import jd.jr.workFlow.response.ReturnValueOfMethod;
import jd.jr.workFlow.utils.CommonTools;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/7.
 */
@Component
public class WorkFlowRunBiz {
    private final static Logger LOG = Logger.getLogger(WorkFlowRunBiz.class);

    @Autowired
    private ProcessDefinitionEntityMapper processDefinitionEntityMapper;
    @Autowired
    private ProcessInstancesEntityMapper processInstancesEntityMapper;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;

    private static String processResourcePath = "workflow"+File.separator;


    /**
     * 启动工作流
     * @param startProcessRequestBo Service中对外暴露的参数
     * @return  ReturnValueOfMethod 方法间的返回参数
     */
    public ReturnValueOfMethod startProcess(StartProcessRequestBo startProcessRequestBo) {
        LOG.info("启动工作流");
        ReturnValueOfMethod returnValueOfMethod = new ReturnValueOfMethod();
        LOG.info("来源系统：" + startProcessRequestBo.getSystemSourceID() + "，业务类型:" + startProcessRequestBo.getBizType() + "，业务单号：" + startProcessRequestBo.getBussinessID());
        LOG.info("查询业务类型所对应的流程模板文件地址");
        if(StringUtils.isEmpty(startProcessRequestBo.getBizType())||StringUtils.isEmpty(startProcessRequestBo.getSystemSourceID())){
            LOG.info("校验参数失败");
            returnValueOfMethod.setSuccess(false);
            returnValueOfMethod.setRespCode(ResponseCodeEnum.Error_100001.getCode());
            returnValueOfMethod.setRespMessage(ResponseCodeEnum.Error_100001.getMessage() +",缺少bizType或者bussinessID");
            return returnValueOfMethod;
        }
        ProcessDefinitionEntity processDefinitionParam = new ProcessDefinitionEntity();
        processDefinitionParam.setBizType(startProcessRequestBo.getBizType());
        processDefinitionParam.setSystemSourceId(startProcessRequestBo.getSystemSourceID());
        List<ProcessDefinitionEntity> processDefinitionEntityList = processDefinitionEntityMapper.selectByParams(processDefinitionParam);
        if (CollectionUtils.isEmpty(processDefinitionEntityList)) {
            LOG.info("查询不到指定的流程模板，请联系管理员添加流程");
            returnValueOfMethod.setSuccess(false);
            returnValueOfMethod.setRespCode(ResponseCodeEnum.Error_200001.getCode());
            returnValueOfMethod.setRespMessage(ResponseCodeEnum.Error_200001.getMessage());
            return returnValueOfMethod;
        } else {
            ProcessDefinitionEntity processDefinitionEntity = processDefinitionEntityList.get(0);
            LOG.info(processDefinitionEntity.getDescription() + ":" + processDefinitionEntity.getTemplatePath());
            //查找流程模板有没有被加载
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                                            .processDefinitionKey(startProcessRequestBo.getBizType())
                                            .processDefinitionTenantId(startProcessRequestBo.getSystemSourceID())
                                            .processDefinitionResourceName(processDefinitionEntity.getTemplatePath())
                                            .list();
            if (CollectionUtils.isEmpty(processDefinitionList)) {// TODO: 2016/7/20 问题：如果改模板后无法重新加载
                LOG.info("流程模板文件未被初始化过，进行初始化");
//                repositoryService.createDeployment().addClasspathResource("D:\\export\\workflow\\VariablesProcess.bpmn20.xml").deploy();
                repositoryService.createDeployment().addClasspathResource(processResourcePath+processDefinitionEntity.getTemplatePath())
                        .tenantId(startProcessRequestBo.getSystemSourceID())
                        .deploy();
            }
            Map<String, Object> initVariables = new HashMap<String, Object>();
            initVariables.put("initer", startProcessRequestBo.getIniter());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(startProcessRequestBo.getBizType(), initVariables,startProcessRequestBo.getSystemSourceID());
            LOG.info("启动流程实例，流程ID: " + processInstance.getId());
            //插入流程实例与业务的对照表
            ProcessInstancesEntity processInstancesEntity = new ProcessInstancesEntity();
            processInstancesEntity.setBizType(startProcessRequestBo.getBizType());
            processInstancesEntity.setBizID(startProcessRequestBo.getBussinessID());
            processInstancesEntity.setProcessIntanceId(processInstance.getId());
            processInstancesEntity.setSystemSourceId(startProcessRequestBo.getSystemSourceID());
            processInstancesEntity.setCreateTime(new Date());
            processInstancesEntity.setProcessStatus(ProcessInstanceStatusEnum.Processing.getCode());
            processInstancesEntityMapper.insert(processInstancesEntity);
            Task inputTask = taskService.createTaskQuery().taskAssignee(startProcessRequestBo.getIniter()).processInstanceId(processInstance.getId()).singleResult();
            Map<String, Object> inputVariables = new HashMap<String, Object>();
            inputVariables.put("initer", startProcessRequestBo.getIniter());
            inputVariables.put("remark", startProcessRequestBo.getRemark());
            taskService.complete(inputTask.getId(), inputVariables);
            LOG.info("初始用户节点自动执行，执行者：" + startProcessRequestBo.getIniter() + ",备注：" + startProcessRequestBo.getRemark());
        }
        return returnValueOfMethod;
    }


    /**
     * 校验订单
     * @param bizID  业务系统订单号
     * @param systemSourceID  来源系统
     * @param bizType 业务类型
     * @return ReturnValueOfMethod 方法间响应对象
     */
    public ReturnValueOfMethod validateBizOrder(String bizID,String systemSourceID,String bizType){
        LOG.info("校验订单：" +systemSourceID+":"+bizType+":"+bizID);
        ReturnValueOfMethod returnValueOfMethod = new ReturnValueOfMethod();
        ProcessInstancesEntity instanceParam = new ProcessInstancesEntity();
        instanceParam.setSystemSourceId(systemSourceID);
        instanceParam.setBizType(bizType);
        instanceParam.setBizID(bizID);
        List<ProcessInstancesEntity> processInstancesEntityList = processInstancesEntityMapper.selectByParams(instanceParam);
        if(CollectionUtils.isEmpty(processInstancesEntityList)){
            LOG.info("数据库中无此订单，可以启动工作流实例");
            returnValueOfMethod.setSuccess(true);
            returnValueOfMethod.setRespMessage(ResponseCodeEnum.Success.getMessage());
            returnValueOfMethod.setRespCode(ResponseCodeEnum.Success.getCode());
        }else{
            returnValueOfMethod.setSuccess(false);
            returnValueOfMethod.setRespMessage(ResponseCodeEnum.Error_100002.getMessage());
            returnValueOfMethod.setRespCode(ResponseCodeEnum.Error_100002.getCode());
            LOG.info("订单已存在");
        }
        return returnValueOfMethod;
    }

    public ReturnValueOfMethod operate(OperateRequestBo operateRequestBo) {
        LOG.info("执行流程操作入参："+operateRequestBo.getSystemSourceID()+":"+operateRequestBo.getBizType()+":"+operateRequestBo.getBussinessID()+":"+operateRequestBo.getUserID());
        ReturnValueOfMethod returnValueOfMethod = new ReturnValueOfMethod();
        ProcessInstancesEntity param = new ProcessInstancesEntity();
        param.setBizID(operateRequestBo.getBussinessID());
        param.setSystemSourceId(operateRequestBo.getSystemSourceID());
        param.setBizType(operateRequestBo.getBizType());
        List<ProcessInstancesEntity> intances = processInstancesEntityMapper.selectByParams(param);
        String instanceID = null;
        String taskID = null;
        if(CollectionUtils.isEmpty(intances)){
            LOG.info("不存在"+operateRequestBo.getBussinessID()+"对应的流程ID");
            returnValueOfMethod.setSuccess(false);
            returnValueOfMethod.setRespCode(ResponseCodeEnum.Error_100003.getCode());
            returnValueOfMethod.setRespMessage(ResponseCodeEnum.Error_100003.getMessage());
            return returnValueOfMethod;
        }else{
            //要求保证一个业务单号对应一个流程实例
            instanceID = intances.get(0).getProcessIntanceId();
        }
        //查询待办
        List<Task> roleGroupList = taskService.createTaskQuery().processInstanceId(instanceID)
                .taskTenantId(operateRequestBo.getSystemSourceID())
                .taskCandidateGroup(operateRequestBo.getRoleGroupID()).list();
        if(CollectionUtils.isEmpty(roleGroupList)){// TODO: 2016/7/20 按照业务系统传入的角色ID处理，业务系统一定要保证传入的角色组正确

            // TODO: 2016/7/20 如果使用角色组查询不到，使用UserID进行查询
            List<Task> assignList = taskService.createTaskQuery().processInstanceId(instanceID)
                    .taskTenantId(operateRequestBo.getSystemSourceID())
                    .taskAssignee(operateRequestBo.getUserID()).list();
            if(CollectionUtils.isEmpty(assignList)){
                LOG.info("查询不到流程ID："+instanceID+",roleGroupID："+operateRequestBo.getRoleGroupID()+"的代办任务节点。");
                returnValueOfMethod.setSuccess(false);
                returnValueOfMethod.setRespCode(ResponseCodeEnum.Error_200002.getCode());
                returnValueOfMethod.setRespMessage(ResponseCodeEnum.Error_200002.getMessage());
                return returnValueOfMethod;
            }else{
                taskID = assignList.get(0).getId();
            }
        }else{
            taskID = roleGroupList.get(0).getId();
            LOG.info("当前流程实例节点ID是："+instanceID+":"+taskID);
        }
        //设置实例流转的参数
        HashMap<String,Object> variables = new HashMap<String, Object>();
        variables.put("remark",operateRequestBo.getRemark());
        variables.put("operate",operateRequestBo.getOperate().getCode());
        variables.put("opinions",operateRequestBo.getOpinions());
        //先签收到用户名下
        taskService.claim(taskID, operateRequestBo.getUserID());
        //在处理节点
        taskService.complete(taskID, variables);
        return returnValueOfMethod;
    }
}