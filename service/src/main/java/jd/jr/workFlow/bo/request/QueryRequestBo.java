package jd.jr.workFlow.bo.request;

import java.io.Serializable;

/**
 * @Autor wangshuo7
 * @Date 2016 /7/13 18:20
 */
public class QueryRequestBo implements Serializable{
    /**
     * 用户userID
     */
    private String userID;
    /**
     * 用户所在角色组ID
     */
    private String roleGroupID;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoleGroupID() {
        return roleGroupID;
    }

    public void setRoleGroupID(String roleGroupID) {
        this.roleGroupID = roleGroupID;
    }

    @Override
    public String toString() {
        return "QueryRequestBo{" +
                "userID='" + userID + '\'' +
                ", roleGroupID='" + roleGroupID + '\'' +
                ", systemSourceID='" + systemSourceID + '\'' +
                ", bussinessID='" + bussinessID + '\'' +
                ", bizType='" + bizType + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
