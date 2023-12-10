import java.util.Scanner;

public class HilbertCurve {
    public static int w[][] = {{0,0,0},
            {0,1,2},
            {0,4,3}};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();

        System.out.println(HilbertNumber(n, x, y));
        in.close();
    }

    public static long HilbertNumber(int n, int x, int y) {
        if (n == 1)
            return w[x][y];

        /*base code:
         * if (n == 0) return 1;
         * is better. because w1 can be divided to 1*1 squares */

        int m = 1 << (n - 1);
        if (x <= m) {
            if (y <= m) {
                return HilbertNumber(n - 1, y, x);//Part 1,coordinates offset:0; flip: x’ = y  y’=x
            } else {
                return m * m + HilbertNumber(n - 1, x, y - m);//Part 2, coordinates offset: y-m 
            }
        } else {
            if (y > m) {
                return 2 * m * m + HilbertNumber(n - 1, x - m, y - m);//Part 3, coordinates offset: x-m y-m
            } else {
                return 3 * m * m + HilbertNumber(n - 1, m + 1 - y, m + 1 - (x - m));//Part4, coordinates offset: x=x-m; flip: x’ = m+1-y,  y’ = m+1-x
            }
        }

    }
}