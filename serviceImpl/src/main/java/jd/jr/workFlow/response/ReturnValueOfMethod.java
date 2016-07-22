package jd.jr.workFlow.response;

import jd.jr.workFlow.enums.ResponseCodeEnum;

/**
 * @Autor wangshuo7
 * @Date 2016/7/15 10:53
 */
public class ReturnValueOfMethod {

    /**
     * 构造函数设置初始值，默认成功
     */
    public ReturnValueOfMethod() {
        this.isSuccess = true;
        this.respCode = ResponseCodeEnum.Success.getCode();
        this.respMessage = ResponseCodeEnum.Success.getMessage();
    }

    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应信息
     */
    private String respMessage;

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

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
