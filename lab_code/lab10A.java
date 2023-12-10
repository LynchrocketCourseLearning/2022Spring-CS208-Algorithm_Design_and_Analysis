import java.io.*;
import java.util.*;

public class lab10A {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        int L = in.nextInt();
        int R = in.nextInt();
        int[] S = new int[N + 1];
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            S[i] = in.nextInt();
            sum += S[i];
        }
        int lo = Math.max(L, sum - R);
        int hi = Math.min(R, sum - L);
        Arrays.sort(S);
        int len = S[S.length - 1];
        int[] W = new int[len + 1];
        for (int i = 1; i <= N; i++) {
            W[S[i]] += 1;
        }
        long[][] OPT = new long[len + 1][hi + 1];
        OPT[0][0] = 1;
        for (int wi = 1; wi <= len; wi++) {
            for (int w = 0; w <= hi; w++) {
                if (wi > w) {
                    OPT[wi][w] = OPT[wi - 1][w];
                } else {
                    for (int k = 0; k <= W[wi]; k++) {
                        if (w - k * wi < 0) break;
                        OPT[wi][w] = (OPT[wi][w] % 1000000007 + OPT[wi - 1][w - k * wi] % 1000000007) % 1000000007;
                    }
                }
            }
        }

        long res = 0;
        for (int i = lo; i <= hi; i++) {
            res = (res % 1000000007 + OPT[len][i] % 1000000007) % 1000000007;
        }

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
