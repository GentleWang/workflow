import jd.jr.workFlow.biz.QueryWorkFlowBiz;
import jd.jr.workFlow.bo.request.OperateRequestBo;
import jd.jr.workFlow.bo.request.QueryRequestBo;
import jd.jr.workFlow.bo.request.StartProcessRequestBo;
import jd.jr.workFlow.bo.response.QueryResponseBo;
import jd.jr.workFlow.bo.response.StartProcessResponseBo;
import jd.jr.workFlow.enums.OperateEnum;
import jd.jr.workFlow.enums.SystemSourceIDEnum;
import jd.jr.workFlow.service.QueryWorkFlowService;
import jd.jr.workFlow.service.WorkFlowRunService;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-config-test.xml")
public class TestWorkFlow {
    public static final Logger log = Logger.getLogger(TestWorkFlow.class);

    @Autowired
    private WorkFlowRunService workFlowRunService;
    @Autowired
    private QueryWorkFlowService queryWorkFlowService;
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

    @Test
    public void testStartProcess(){
        StartProcessRequestBo startProcessRequestBo = new StartProcessRequestBo();
        startProcessRequestBo.setBizType("financeAssets1");
        startProcessRequestBo.setSystemSourceID(SystemSourceIDEnum.TradeCenter.getCode());
        startProcessRequestBo.setIniter("inputer2");
        startProcessRequestBo.setRemark("inputer2 录入资产");
        startProcessRequestBo.setBussinessID("finance_0003");
        StartProcessResponseBo startProcessResponseBo =workFlowRunService.startProcessInstance(startProcessRequestBo);
        log.info(startProcessResponseBo.toString());
    }
    @Test
    public void testQueryToDo(){
        QueryRequestBo queryRequestBo = new QueryRequestBo();
//        queryRequestBo.setUserID("operator1");
        queryRequestBo.setRoleGroupID("Operaters");
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryMyTask(queryRequestBo);
        System.out.println(queryResponseBo.toString());

    }
    @Test
    public void testExecuteWorkFlow(){
        OperateRequestBo operateRequestBo = new OperateRequestBo();
        operateRequestBo.setBizType("financeAssets1");
        operateRequestBo.setSystemSourceID(SystemSourceIDEnum.TradeCenter.getCode());
        operateRequestBo.setOpinions("同意");
        operateRequestBo.setOperate(OperateEnum.AGREE);
        operateRequestBo.setRemark("运营初审");
        operateRequestBo.setBussinessID("finance_0002");
        operateRequestBo.setUserID("operator1");
        operateRequestBo.setRoleGroupID("Operaters");
        workFlowRunService.executeWorkFlow(operateRequestBo);

    }

    @Test
    public void testQueryHadDone(){
        QueryRequestBo queryRequestBo = new QueryRequestBo();
        queryRequestBo.setUserID("operator1");
//        queryRequestBo.setRoleGroupID("Operaters");
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryMyHadDode(queryRequestBo);
        System.out.println(queryResponseBo.toString());
    }
    @Test
    public void testQueryProcessIntenceDetail(){
        QueryRequestBo queryRequestBo = new QueryRequestBo();
        queryRequestBo.setBizType("financeAssets");
        queryRequestBo.setSystemSourceID(SystemSourceIDEnum.TradeCenter.getCode());
        queryRequestBo.setBussinessID("finance_0001");
//        queryRequestBo.setRoleGroupID("Operaters");
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryProcessIntenceDetail(queryRequestBo);
        System.out.println(queryResponseBo.toString());
    }

    // 历史活动查看(某一次流程的执行经历的多少步)
    @Test
    public void queryHistoricActivityInstance() throws Exception {
        String processInstanceId = "1401";
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .processInstanceId("5018")
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().asc()
                // 执行查询
                .list();
        for (HistoricActivityInstance hai : hais) {
            System.out.print("activitiId:" + hai.getActivityId()+"，");
            System.out.print("name:" + hai.getActivityName()+"，");
            System.out.print("type:" + hai.getActivityType()+"，");
            System.out.print("pid:" + hai.getProcessInstanceId()+"，");
            System.out.print("assignee:" + hai.getAssignee()+"，");
            System.out.print("startTime:" + hai.getStartTime()+"，");
            System.out.print("endTime:" + hai.getEndTime()+"，");
            System.out.println("duration:" + hai.getDurationInMillis());
            System.out.println("ActivityType:" + hai.getActivityType());
        }
    }

    // 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
    @Test
    public void queryHistoricProcessInstance() throws Exception {
        // 获取历史流程实例的查询对象
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        // 设置查询参数
        historicProcessInstanceQuery
                //过滤条件
                .processDefinitionKey("financeAssets")
                // 分页条件
//     .listPage(firstResult, maxResults)
                // 排序条件
                .orderByProcessInstanceStartTime().desc();
        // 执行查询
        List<HistoricProcessInstance> hpis = historicProcessInstanceQuery.list();
        // 遍历查看结果
        for (HistoricProcessInstance hpi : hpis) {
            System.out.print("pid:" + hpi.getId()+",");
            System.out.print("pdid:" + hpi.getProcessDefinitionId()+",");
            System.out.print("startTime:" + hpi.getStartTime()+",");
            System.out.print("endTime:" + hpi.getEndTime()+",");
            System.out.print("duration:" + hpi.getDurationInMillis()+",");
            System.out.println("vars:" + hpi.getProcessVariables());
        }

    }

    // 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
    @Test
    public void queryHistoricTaskInstance() throws Exception {
        // 获取历史流程实例的查询对象
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        // 设置查询参数
        historicTaskInstanceQuery
                //过滤条件
//                .processDefinitionKey("financeAssets")
                .processDefinitionKey("5017")
                // 分页条件
//     .listPage(firstResult, maxResults)
                // 排序条件
                .orderByHistoricTaskInstanceEndTime().desc();
        // 执行查询
        List<HistoricTaskInstance> hpis = historicTaskInstanceQuery.list();
        // 遍历查看结果
        for (HistoricTaskInstance hpi : hpis) {
            System.out.print("LocalVariables:" + hpi.getTaskLocalVariables()+",");
            System.out.print("Description:" + hpi.getDescription()+",");
            System.out.print("Assignee:" + hpi.getAssignee()+",");
            System.out.print("pdid:" + hpi.getProcessDefinitionId()+",");
            System.out.print("startTime:" + hpi.getStartTime()+",");
            System.out.print("endTime:" + hpi.getEndTime()+",");
            System.out.print("duration:" + hpi.getDurationInMillis()+",");
            System.out.println("vars:" + hpi.getProcessVariables());
        }

    }

    @Test
    public void viewVar() throws Exception {
        String processInstanceId = "1901";
        Task task =taskService.createTaskQuery().taskId("12510").singleResult();
        System.out.println("taskName:" + task.getName());
//        String variableName = "请假人";
//        String val = (String)taskService.getVariable(task.getId(), variableName );
        Map<String,Object> vars = taskService.getVariables(task.getId());
        for (String variableName : vars.keySet()) {
            String val = (String) vars.get(variableName);
            System.out.println(variableName + " = " +val);
        }
    }


    @Test
    public  void setProcessIniter(){
        System.out.println("开始录入资产");
        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
//        String processID =  runtimeService.startProcessInstanceByKey("financeAssets").getId();
//        System.out.println("新建流程ID"+processID);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("ipnuterGroup", "inputer1");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financeAssets", variables);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("financeAssets", variables,userID);

        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("ProcessInstance: " + processInstance.getId());
        Map<String, Object> initVariables = new HashMap<String, Object>();
        Task inputTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("inputer1").singleResult();
        System.out.println(inputTask.getId());
        Map<String, Object> inputVariables = new HashMap<String, Object>();
        inputVariables.put("ipnuterGroup", "inputer1");
        inputVariables.put("assetID", 1111);
        inputVariables.put("notices", "inputer1"+" 录入虚拟资产");
        taskService.complete(inputTask.getId(),inputVariables);
    }

}
