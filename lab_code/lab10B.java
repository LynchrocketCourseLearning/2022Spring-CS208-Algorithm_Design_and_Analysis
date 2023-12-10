import java.io.*;
import java.util.*;

public class lab10B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        int[] a = new int[N + 1];
        a[1] = in.nextInt();
        int cnt = 1;
        for (int i = 2; i <= N; i++) {
            int tmp = in.nextInt();
            if (a[cnt] == tmp) continue;
            a[cnt + 1] = tmp;
            cnt++;
        }

        int[][] OPT = new int[cnt + 1][cnt + 1];
        int i = 1, j = 1, count = 1;
        while (count <= cnt) {
            if (i == j) OPT[i][j] = 0;
            else if (a[i] == a[j]) OPT[i][j] = OPT[i + 1][j - 1] + 1;
            else if (a[i] != a[j]) OPT[i][j] = Math.min(OPT[i + 1][j] + 1, OPT[i][j - 1] + 1);

            if (j == cnt) {
                count++;
                i = 1;
                j = count;
            } else {
                i++;
                j++;
            }
        }

        out.print(OPT[1][cnt]);
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
