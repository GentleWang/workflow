package jd.jr.workFlow.bo.response;

/**
 * @Autor wangshuo7
 * @Date 2016/7/15 10:10
 */
public class StartProcessResponseBo {
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

    @Override
    public String toString() {
        return "StartProcessResponseBo{" +
                "respCode='" + respCode + '\'' +
                ", respMessage='" + respMessage + '\'' +
                ", systemSourceID='" + systemSourceID + '\'' +
                ", bussinessID='" + bussinessID + '\'' +
                ", bizType='" + bizType + '\'' +
                '}';
    }
}
