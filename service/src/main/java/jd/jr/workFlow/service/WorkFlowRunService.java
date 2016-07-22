package jd.jr.workFlow.service;

import jd.jr.workFlow.bo.request.OperateRequestBo;
import jd.jr.workFlow.bo.request.StartProcessRequestBo;
import jd.jr.workFlow.bo.response.OperateResponseBo;
import jd.jr.workFlow.bo.response.StartProcessResponseBo;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 16:37
 */
public interface WorkFlowRunService {

    public StartProcessResponseBo startProcessInstance(StartProcessRequestBo startProcessRequestBo);

    public OperateResponseBo executeWorkFlow(OperateRequestBo operateRequestBo);
}
