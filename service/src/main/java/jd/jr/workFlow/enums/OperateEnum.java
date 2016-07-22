package jd.jr.workFlow.enums;

/**
 * @Autor wangshuo7
 * @Date 2016/7/13 18:34
 */
public enum OperateEnum {

    AGREE("YES","同意"),
    REFUSE("NO","拒绝"),
    RETURN("BACK","退回");


    private String code;
    private String name;

    OperateEnum(String code,String name) {
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
