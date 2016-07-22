package jd.jr.workFlow.bo.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshuo7 on 2016/7/13.
 */
public class QueryResponseBo implements Serializable{
    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应信息
     */
    private String respMessage;
    /**
     * 来源系统ID
     */
    private String systemSourceID;
    /**
     * 业务ID
     */
    private String bussinessID;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 查询到的任务节点集合
     */
    private ArrayList<TaskResultBo> taskResultList;


    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getSystemSourceID() {
        return systemSourceID;
    }

    public void setSystemSourceID(String systemSourceID) {
        this.systemSourceID = systemSourceID;
    }

    public String getBussinessID() {
        return bussinessID;
    }

    public void setBussinessID(String bussinessID) {
        this.bussinessID = bussinessID;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public ArrayList<TaskResultBo> getTaskResultList() {
        return taskResultList;
    }

    public void setTaskResultList(ArrayList<TaskResultBo> taskResultList) {
        this.taskResultList = taskResultList;
    }


}
