import java.io.*;
import java.util.*;

public class lab6B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        String s = Integer.toBinaryString(N);
        int len = s.length();

        int level = (len + 1);

        int[][] odd = new int[level][N];
        int[][] even = new int[level][N];
        int[] idx_odd = new int[level];
        int[] idx_even = new int[level];
        for (int num = 1; num <= N; num++) {
            String tmp = Integer.toBinaryString(num);
            if (tmp.length() < len) {
                StringBuilder sb = new StringBuilder(tmp);
                for (int i = tmp.length(); i < len; i++) {
                    sb.insert(0, '0');
                }
                tmp = sb.toString();
            }
            int tmp_len = tmp.length();
            for (int i = tmp_len; i >= 1; i--) {
                if (tmp.charAt(i - 1) == '1') {
                    idx_odd[tmp_len - i + 1]++;
                    odd[tmp_len - i + 1][idx_odd[tmp_len - i + 1]] = num;
                } else {
                    idx_even[tmp_len - i + 1]++;
                    even[tmp_len - i + 1][idx_even[tmp_len - i + 1]] = num;
                }
            }
        }

        if (N == 1) {
            out.println(0);
        } else if (N <= 12) {
            out.println(N);
            for (int i = 1; i <= N; i++) {
                out.println(1 + " " + i);
                out.print(N - 1 + " ");
                for (int j = 1; j <= N; j++) {
                    if (j == i) continue;
                    out.print(j + " ");
                }
                out.println("");
            }
        } else {
            out.println(2 * len);
            for (int i = 1; i < level; i++) {
                out.print(idx_odd[i] + " ");
                for (int j = 1; j <= idx_odd[i]; j++) {
                    out.print(odd[i][j] + " ");
                }
                out.println("");
                out.print(idx_even[i] + " ");
                for (int j = 1; j <= idx_even[i]; j++) {
                    out.print(even[i][j] + " ");
                }
                out.println("");
                out.print(idx_even[i] + " ");
                for (int j = 1; j <= idx_even[i]; j++) {
                    out.print(even[i][j] + " ");
                }
                out.println("");
                out.print(idx_odd[i] + " ");
                for (int j = 1; j <= idx_odd[i]; j++) {
                    out.print(odd[i][j] + " ");
                }
                out.println("");
            }
        }

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
