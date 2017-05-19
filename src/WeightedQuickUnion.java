/**
 * Weighted Quick-Union 加权快速并集
 * <p>
 * Created by Draper_HXY on 2017/5/18 20:10.
 * Email: Draper_HXY@163.com.
 */
public class WeightedQuickUnion {

    private static int[] res = new int[10];
    private static int[] weight = new int[10];

    public static int[] excute1(int[][] arr) {
        res = Utils.init(res);

        //用来存储节点数，从而保证小树向大树合并
        for (int i = 0; i < weight.length; i++) {
            weight[i] = 1;
        }

        for (int i = 0; i < arr.length; i++) {
            int frontRoot = arr[i][0];
            int tailRoot = arr[i][1];

            frontRoot = find(frontRoot);
            tailRoot = find(tailRoot);

            //不是同一集合需要合并
            if (frontRoot != tailRoot) {
                //小树合并到大树上
                if (weight[frontRoot] < weight[tailRoot]) {
                    union(frontRoot, tailRoot);
                } else {
                    union(tailRoot, frontRoot);
                }
            }
        }
        return res;
    }

    public static int find(int root) {
        int count = 1;//用来记录树节点中节点数目
        while (res[root] != root) {
            count++;
            root = res[root];
        }
        weight[root] = count;
        return res[root];
    }

    public static void union(int frontRoot, int tailRoot) {
        res[frontRoot] = tailRoot;
    }
}
