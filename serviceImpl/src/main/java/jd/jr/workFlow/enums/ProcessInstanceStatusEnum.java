package jd.jr.workFlow.enums;

/**
 * @Autor wangshuo7
 * @Date 2016/7/18 10:09
 * @Description 流程实例状态枚举
 */
public enum ProcessInstanceStatusEnum {

    INIT("0","开始"),
    Processing("1","流转中"),
    END("2","结束"),
    TERMINATE("3","终止");
    private String code;
    private String name;

    ProcessInstanceStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
