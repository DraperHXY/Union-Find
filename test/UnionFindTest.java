import org.junit.jupiter.api.Test;

/**
 * Created by Draper_HXY on 2017/5/18 20:54.
 * Email: Draper_HXY@163.com.
 */
public class UnionFindTest {

    @Test
    public void tzest() {
        int[] res1 = QuickFind.excute3(TestData.data);//快速查找
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        int[] res2 = QuickUnion.excute1(TestData.data, 10);//快速合并
        int[] res3 = WeightedQuickUnion.excute1(TestData.data);//加权快速合并
//        Utils.printAll(res1);
//        Utils.printAll(res2);
//        Utils.printAll(res3);


    }
}
