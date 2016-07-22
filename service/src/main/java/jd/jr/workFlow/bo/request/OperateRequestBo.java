package jd.jr.workFlow.bo.request;

import jd.jr.workFlow.enums.OperateEnum;

import java.io.Serializable;

/**
 *
 * @Autor wangshuo7
 * @Date 2016 /7/13 18:49
 */
public class OperateRequestBo implements Serializable{
    /**
     * 用户userID
     */
    private String userID;
    /**
     * 用户所在角色组ID
     */
    private String roleGroupID;
    /**
     * 执行操作
     */
    private OperateEnum operate;

     /**
     * 处理意见
     */
    private String opinions;
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

    public OperateEnum getOperate() {
        return operate;
    }

    public void setOperate(OperateEnum operate) {
        this.operate = operate;
    }

    public String getOpinions() {
        return opinions;
    }

    public void setOpinions(String opinions) {
        this.opinions = opinions;
    }
}
