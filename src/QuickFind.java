/**
 * Quick-Find 快速查找
 * <p>
 * Created by Draper_HXY on 2017/5/17 18:17.
 * Email: Draper_HXY@163.com.
 */
public class QuickFind {

    private static int[] res = new int[10];


    public static int[] excute1(int[][] data) {
        res = Utils.init(res);

        for (int i = 0; i < data.length; i++) {
            int first = data[i][0];//前者数字
            int next = data[i][1];//后者数字

            int temp = res[first];
            for (int j = 0; j < res.length; j++) {

                if (res[j] == temp) {
                    res[j] = res[next];//与前者数字相同的集合合并到后者
                }
            }

            for (int k = 0; k < res.length; k++) {
                System.out.print("|" + res[k]);
            }
            System.out.println();

        }
        return res;
    }

    public static int[] excute2(int[][] data) {
        res = Utils.init(res);//初始化答案数组

        for (int i = 0; i < data.length; i++) {
            int firstValue = data[i][0];
            int nextValue = data[i][1];

            //快速查找细节，将与前一个数组值相等的数合并到后面的这个数组
            int var = res[firstValue];//前一个集合的值都为 var
            for (int i1 = 0; i1 < res.length; i1++) {
                if (res[i1] == var) {
                    res[i1] = res[nextValue];
                }
            }
        }

        Utils.printAll(res);
        return res;
    }

    //经过简单的优化后的算法
    public static int[] excute3(int[][] data) {
        res = Utils.init(res);
        for (int i = 0; i < data.length; i++) {
            int p = data[i][0];
            int q = data[i][1];
            //优化  如果相等则不需要遍历
            if (res[p] == res[q]) {
                continue;
            }
            int j, temp;
            for (j = 0, temp = res[p]; j < res.length; j++) {
                if (res[j] == temp) {
                    res[j] = res[q];
                }
            }
        }
        return res;
    }
}
