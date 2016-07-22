package jd.jr.workFlow.enums;

/**
 * @Autor wangshuo7
 * @Date 2016/7/14 14:18
 */
public enum SystemSourceIDEnum {

    TradeCenter("TradeCenter","交易所");
    private String code;
    private String name;

    SystemSourceIDEnum(String code, String name) {
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
