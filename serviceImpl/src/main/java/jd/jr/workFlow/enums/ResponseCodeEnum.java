package jd.jr.workFlow.enums;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 15:00
 */
public enum ResponseCodeEnum {


    Success("000000","success","成功"),

    Error_100000("100000","fail","失败"),
    Error_100001("100001","参数不全","校验参数失败"),
    Error_100002("100002","订单已存在","幂等校验失败"),
    Error_100003("100003","流程实例不存在","未找到业务单号对应的流程实例"),

    Error_200001("200001","未定义流程模板","数据库中没有定义流程模板，请联系管理员"),
    Error_200002("200002","用户任务节点不存在","查找不到指定流程指定用户的任务节点");

    private String code;
    private String message;
    private String description;

    ResponseCodeEnum(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
