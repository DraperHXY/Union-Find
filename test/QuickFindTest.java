import org.junit.jupiter.api.Test;

/**
 * Created by Draper_HXY on 2017/5/17 18:12.
 * Email: Draper_HXY@163.com.
 */

public class QuickFindTest {

    @Test
    public void testPrintAll() {
        int[] res1 = QuickFind.excute1(TestData.data);
        int[] res2 = QuickFind.excute2(TestData.data);
        int[] res3 = QuickFind.excute3(TestData.data);
        Utils.printAll(res1);
        Utils.printAll(res2);
        Utils.printAll(res3);
    }

    @Test
    public void testArrLength(){
        System.out.println(TestData.data.length);
    }

}
