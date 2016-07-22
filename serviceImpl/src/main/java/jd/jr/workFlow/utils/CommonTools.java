package jd.jr.workFlow.utils;

/**
 * @Autor wangshuo7
 * @Date 2016/7/22 16:58
 */
public class CommonTools {

    public static String getClassPath(){
        return CommonTools.class.getClassLoader().getResource("/").getPath();
    }
}
