package jd.jr.workFlow.bo.request;

import java.io.Serializable;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 17:03
 */
public class StartProcessRequestBo implements Serializable{

    /**
     * 流程启动者
     */
    private String initer;
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
     * 备注
     */
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIniter() {
        return initer;
    }

    public void setIniter(String initer) {
        this.initer = initer;
    }

    @Override
    public String toString() {
        return "StartProcessRequestBo{" +
                "initer='" + initer + '\'' +
                ", systemSourceID='" + systemSourceID + '\'' +
                ", bussinessID='" + bussinessID + '\'' +
                ", bizType='" + bizType + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
