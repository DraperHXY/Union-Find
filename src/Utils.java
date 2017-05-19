/**
 * Created by Draper_HXY on 2017/5/17 18:06.
 * Email: Draper_HXY@163.com.
 */
public class Utils {
    private static int[] temp = new int[10];

    public static int[] init(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        return arr;
    }

    public static void printAll(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println("\n");
    }

}
