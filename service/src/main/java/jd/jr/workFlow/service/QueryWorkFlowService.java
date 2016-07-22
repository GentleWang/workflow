package jd.jr.workFlow.service;

import jd.jr.workFlow.bo.request.QueryRequestBo;
import jd.jr.workFlow.bo.response.QueryResponseBo;

/**
 * Created by wangshuo7 on 2016/7/13.
 */
public interface QueryWorkFlowService {

    /**
     * 查询我的待办
     * @param queryRequestBo 入参
     * @return QueryResponseBo 查询我的待办出
     */
    public QueryResponseBo queryMyTask(QueryRequestBo queryRequestBo);

    /**
     * 查询我的已办接口
     * @param queryRequestBo
     * @return
     */
    public QueryResponseBo queryMyHadDode(QueryRequestBo queryRequestBo);

    /**
     * 查询流程所有的节点信息
     * @param queryRequestBo
     * @return
     */
    public QueryResponseBo queryProcessIntenceDetail(QueryRequestBo queryRequestBo);
}
