package jd.jr.workFlow.service.Impl;

import com.jd.fastjson.JSON;
import jd.jr.workFlow.biz.QueryWorkFlowBiz;
import jd.jr.workFlow.bo.request.QueryRequestBo;
import jd.jr.workFlow.bo.response.QueryResponseBo;
import jd.jr.workFlow.enums.ResponseCodeEnum;
import jd.jr.workFlow.service.QueryWorkFlowService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangshuo7 on 2016/7/13.
 */
@Component("queryWorkFlowService")
public class QueryWorkFlowServiceImpl implements QueryWorkFlowService {
    private final static Logger LOG = Logger.getLogger(QueryWorkFlowServiceImpl.class);

    @Autowired
    QueryWorkFlowBiz queryWorkFlowBiz;

    /**
     * 查询我的待办
     * @param queryRequestBo 入参
     * @return QueryResponseBo 查询我的待办出
     */
    public QueryResponseBo queryMyTask(QueryRequestBo queryRequestBo){
        LOG.info("查询待办："+queryRequestBo.toString());
        // TODO: 2016/7/18 校验参数
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        try {
            queryResponseBo = queryWorkFlowBiz.queryToDo(queryRequestBo);
        } catch (Exception e) {
            queryResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getCode());
            queryResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getMessage());
            LOG.error("查询待办出现异常",e);
        }
        LOG.info("待办列表："+JSON.toJSONString(queryResponseBo));
        return queryResponseBo;
    }

    /**
     * 查询我的已办接口
     * @param queryRequestBo
     * @return
     */
    public QueryResponseBo queryMyHadDode(QueryRequestBo queryRequestBo){
        LOG.info("查询已办入参："+queryRequestBo.toString());
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        try {
            queryResponseBo = queryWorkFlowBiz.queryHadDone(queryRequestBo);
        } catch (Exception e) {
            queryResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getCode());
            queryResponseBo.setRespMessage(ResponseCodeEnum.Error_100000.getMessage());
            LOG.error("查询已办时发生异常",e);
        }
        LOG.info("已办列表："+JSON.toJSONString(queryResponseBo));
        return queryResponseBo;
    }

    /**
     * 查询流程所有的节点信息
     * @param queryRequestBo
     * @return
     */
    //TODO: 2016/7/19 参数校验
    public QueryResponseBo queryProcessIntenceDetail(QueryRequestBo queryRequestBo){
        LOG.info("查询已办入参："+queryRequestBo.toString());
        QueryResponseBo queryResponseBo = new QueryResponseBo();
        try {
            queryResponseBo = queryWorkFlowBiz.queryProcessIntenceDetail(queryRequestBo);
        } catch (Exception e) {
            queryResponseBo.setRespCode(ResponseCodeEnum.Error_100000.getCode());
            queryResponseBo.setRespMessage(ResponseCodeEnum.Error_100000.getMessage());
            LOG.error("查询流程实例列表时发生异常",e);
        }
        LOG.info("查询流程实例列表结果："+JSON.toJSONString(queryResponseBo).toString());
        return queryResponseBo;
    }

}

