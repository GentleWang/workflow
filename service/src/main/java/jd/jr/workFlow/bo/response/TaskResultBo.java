package jd.jr.workFlow.bo.response;

import jd.jr.workFlow.enums.OperateEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 11:04
 */
public class TaskResultBo implements Serializable{

    /**
     * 节点ID
     */
    private String taskID;

    /**
     * 节点名称
     */
    private String taskName;

    /**
     * 节点描述
     */
    private String description;

    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 业务ID
     */
    private String bussinessID;

    /**
     * 用户UserID
     */
    private String userID;
    /**
     * 用户角色组ID
     */
    private String roleGroupID;

    /**
     * 节点开始时间
     */
    private Date createTime;
    /**
     * 节点结束时间
     */
    private Date endTime;
    /**
     * 节点执行时间
     */
    private long duration;

    /**
     * 流程启动人
     */
    private String processInstanceIniter;

    /**
     * 执行操作
     */
    private OperateEnum operate;
    /**
     * 处理意见
     */
    private String opinions;

    /**
     * 上一个节点执行者
     */
    private String previousOperator;

    private String remark;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBussinessID() {
        return bussinessID;
    }

    public void setBussinessID(String bussinessID) {
        this.bussinessID = bussinessID;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getProcessInstanceIniter() {
        return processInstanceIniter;
    }

    public void setProcessInstanceIniter(String processInstanceIniter) {
        this.processInstanceIniter = processInstanceIniter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getPreviousOperator() {
        return previousOperator;
    }

    public void setPreviousOperator(String previousOperator) {
        this.previousOperator = previousOperator;
    }

    @Override
    public String toString() {
        return "TaskResultBo{" +
                "taskID='" + taskID + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", bussinessID='" + bussinessID + '\'' +
                ", userID='" + userID + '\'' +
                ", roleGroupID='" + roleGroupID + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", processInstanceIniter='" + processInstanceIniter + '\'' +
                ", operate=" + operate +
                ", opinions='" + opinions + '\'' +
                ", previousOperator='" + previousOperator + '\'' +
                '}';
    }
}
