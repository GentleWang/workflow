package jd.jr.workFlow.model;

import java.util.Date;

public class ProcessInstancesEntity {
    private String id;

    private String bizID;

    private String bizType;

    private String systemSourceId;

    private String processStatus;

    private String processIntanceId;

    private String processTaskId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBizID() {
        return bizID;
    }

    public void setBizID(String bizID) {
        this.bizID = bizID == null ? null : bizID.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getSystemSourceId() {
        return systemSourceId;
    }

    public void setSystemSourceId(String systemSourceId) {
        this.systemSourceId = systemSourceId == null ? null : systemSourceId.trim();
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public String getProcessIntanceId() {
        return processIntanceId;
    }

    public void setProcessIntanceId(String processIntanceId) {
        this.processIntanceId = processIntanceId == null ? null : processIntanceId.trim();
    }

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId == null ? null : processTaskId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}