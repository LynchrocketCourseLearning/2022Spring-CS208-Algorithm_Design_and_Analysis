import java.io.*;
import java.util.*;

public class lab9A {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();

        int depth = (Math.min(n, m) + 1) >> 1;
        int len = Math.max(n, m);
        long[][] f = new long[depth + 1][len + 1];
        long[][] s1 = new long[depth + 1][len + 1];
        long[][] s2 = new long[depth + 1][len + 1];
        for (int i = 1; i <= len; i++) {
            f[1][i] = 1;
            s1[1][i] = s1[1][i - 1] + f[1][i];
            s2[1][i] = s2[1][i - 1] + i * f[1][i];
        }

        for (int i = 2; i <= depth; i++) {
            for (int j = 1; j <= len; j++) {
                if (j == 1) {
                    f[i][j] = 0;
                } else {
                    f[i][j] = (((j - 1) % 1000000007 * s1[i - 1][j - 2] % 1000000007) % 1000000007 - s2[i - 1][j - 2] % 1000000007) % 1000000007;
                    s1[i][j] = (s1[i][j - 1] % 1000000007 + f[i][j] % 1000000007) % 1000000007;
                    s2[i][j] = (s2[i][j - 1] % 1000000007 + (j % 1000000007 * f[i][j] % 1000000007) % 1000000007) % 1000000007;
                }
            }
        }
        long res = 0;
        for (int i = 1; i <= depth; i++) {
            res = (res % 1000000007 + (f[i][n] % 1000000007 * f[i][m] % 1000000007) % 1000000007) % 1000000007;
        }
        out.print((res > 0) ? res : (res + 1000000007));

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
