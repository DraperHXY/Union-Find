/**
 * Quick-Union 快速并集
 * <p>
 * Created by Draper_HXY on 2017/5/18 14:44.
 * Email: Draper_HXY@163.com.
 */
public class QuickUnion {

    /**
     * @param data   模拟输入的数据
     * @param length 连通的对象数
     * @return
     */
    public static int[] excute1(int[][] data, int length) {
        int[] res = new int[length];
        res = Utils.init(res);

        for (int i = 0; i < data.length; i++) {
            int frontRoot = data[i][0];
            int tailRoot = data[i][1];

            //寻找做合并的根
            frontRoot = find(frontRoot, res);
            tailRoot = find(tailRoot, res);

            union(frontRoot, tailRoot, res);
            Utils.printAll(res);
        }

        return res;
    }

    public static int find(int root, int[] data) {
        if (data[root] != root) {
            find(data[root], data);
        }
        return data[root];
    }

    public static void union(int front, int tail, int[] data) {
        data[front] = data[tail];
    }
}
