import jd.jr.workFlow.utils.CommonTools;
import org.junit.Test;

/**
 * @Autor wangshuo7
 * @Date 2016/7/22 17:00
 */

public class TestTools {


    @Test
    public void testPath(){
        System.out.println(TestTools.class.getResource("/").getPath());
    }
}
