import java.io.*;
import java.util.*;

public class lab9B {
    public static long[][] mul(long[][] a, long[][] b, int M) {
        long[][] res = new long[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < M; k++) {
                    res[i][j] = (res[i][j] % 1000000007 + (a[i][k] % 1000000007 * b[k][j] % 1000000007) % 1000000007) % 1000000007;
                }
            }
        }
        return res;
    }

    public static long pow(int M, long N) {
        long[][] base = new long[M][M];
        long[][] ans = new long[M][M];
        for (int i = 0; i < M; i++) {
            base[i][0] = 1;
            ans[i][0] = 1;
            if (i < M - 1) {
                base[i][i + 1] = 1;
                ans[i][i + 1] = 1;
            }
        }
        while (N > 0) {
            if ((N & 1) == 1) {
                ans = mul(ans, base, M);
            }
            base = mul(base, base, M);
            N >>= 1;
        }
        long res = 0;
        for (int i = 0; i < M; i++) {
            res = (res + ans[0][i]) % 1000000007;
        }
        return res;
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        long N = in.nextInt();
        int M = in.nextInt();

        long res = pow(M, N-1);

        out.print(res);

        out.close();
    }

    static class QReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class QWriter implements Closeable {
        private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException e) {
                return;
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException e) {
                return;
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                return;
            }
        }
    }
}
