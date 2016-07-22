package jd.jr.workFlow.service.Impl;

import com.jd.fastjson.JSON;
import jd.jr.workFlow.biz.WorkFlowRunBiz;
import jd.jr.workFlow.bo.request.OperateRequestBo;
import jd.jr.workFlow.bo.request.StartProcessRequestBo;
import jd.jr.workFlow.bo.response.OperateResponseBo;
import jd.jr.workFlow.bo.response.StartProcessResponseBo;
import jd.jr.workFlow.enums.ResponseCodeEnum;
import jd.jr.workFlow.response.ReturnValueOfMethod;
import jd.jr.workFlow.service.WorkFlowRunService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 17:36
 */
@Component("workFlowRunService")
public class WorkFlowRunServiceImpl implements WorkFlowRunService {

    private final static Logger LOG = Logger.getLogger(WorkFlowRunServiceImpl.class);

    @Autowired
    private WorkFlowRunBiz workFlowRunBiz;

    /**
     * 启动工作流
     * @param startProcessRequestBo 启动工作流参数
     * @return
     */
    public StartProcessResponseBo startProcessInstance(StartProcessRequestBo startProcessRequestBo) {
        LOG.info("启动工作流："+ JSON.toJSONString(startProcessRequestBo));
        StartProcessResponseBo  startProcessResponseBo = new StartProcessResponseBo();
        startProcessResponseBo.setRespCode(ResponseCodeEnum.Success.getCode());
        startProcessResponseBo.setRespMessage(ResponseCodeEnum.Success.getMessage());
        startProcessResponseBo.setBizType(startProcessRequestBo.getBizType());
        startProcessResponseBo.setBussinessID(startProcessRequestBo.getBussinessID());
        startProcessResponseBo.setSystemSourceID(startProcessRequestBo.getSystemSourceID());
        try {
            ReturnValueOfMethod returnValueOfMethod = null;
            returnValueOfMethod = workFlowRunBiz.validateBizOrder(startProcessRequestBo.getBussinessID(),startProcessRequestBo.getSystemSourceID(),startProcessRequestBo.getBizType());
            if(returnValueOfMethod.isSuccess()){
                returnValueOfMethod = workFlowRunBiz.startProcess(startProcessRequestBo);
                startProcessResponseBo.setRespCode(returnValueOfMethod.getRespCode());
                startProcessResponseBo.setRespMessage(returnValueOfMethod.getRespMessage());
            }else{
                LOG.info("启动工作流参数校验失败："+returnValueOfMethod.getRespMessage());
                startProcessResponseBo.setRespCode(returnValueOfMethod.getRespCode());
                startProcessResponseBo.setRespMessage(returnValueOfMethod.getRespMessage());
            }
        } catch (Exception e) {
            startProcessResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getCode());
            startProcessResponseBo.setRespMessage(ResponseCodeEnum.Error_100000.getMessage());
            LOG.error("启动工作流出现异常",e);
        }
        LOG.info("启动工作流出参：" + JSON.toJSONString(startProcessResponseBo));
        return startProcessResponseBo;
    }

    /**
     * 执行工作流
     * @param operateRequestBo 操作请求参数
     * @return OperateResponseBo 操作反馈结果
     */
    public OperateResponseBo executeWorkFlow(OperateRequestBo operateRequestBo){
        LOG.info("执行工作流入参："+ JSON.toJSONString(operateRequestBo));
        OperateResponseBo operateResponseBo = new OperateResponseBo();
        operateResponseBo.setBussinessID(operateRequestBo.getBussinessID());
        operateResponseBo.setBizType(operateRequestBo.getBizType());
        operateResponseBo.setSystemSourceID(operateRequestBo.getSystemSourceID());
        operateResponseBo.setRespCode(ResponseCodeEnum.Success.getCode());
        operateResponseBo.setRespMessage(ResponseCodeEnum.Success.getMessage());
        try {
            ReturnValueOfMethod returnValueOfMethod = null;
            // TODO: 2016/7/19 校验参数
            returnValueOfMethod = workFlowRunBiz.operate(operateRequestBo);
            operateResponseBo.setRespCode(returnValueOfMethod.getRespCode());
            operateResponseBo.setRespMessage(returnValueOfMethod.getRespMessage());
        } catch (Exception e) {
            operateResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getCode());
            operateResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getMessage());
            LOG.error("执行工作流出现异常",e);
        }
        LOG.info("执行工作流出参："+operateResponseBo);
        return operateResponseBo;
    }
}
