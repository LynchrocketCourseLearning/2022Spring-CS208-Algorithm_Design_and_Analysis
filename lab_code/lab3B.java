import java.io.*;
import java.util.*;

public class lab3B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        int M = in.nextInt();
        int C = in.nextInt();
        long T = in.nextLong();
        long[] bunny = new long[N + 1];
        long[] nest = new long[M + 1];
        for (int i = 1; i <= N; i++) {
            bunny[i] = in.nextLong();
        }
        for (int i = 1; i <= M; i++) {
            nest[i] = in.nextLong();
        }
        Arrays.sort(bunny);
        Arrays.sort(nest);
        long res = 0;
        int cnt = 0;
        int cur_bunny = 1, cur_nest = 1;
        while (cur_bunny <= N && cur_nest <= M) {
            if (cnt == C) {
                cur_nest++;
                cnt = 0;
            }
            if(cur_nest > M) break;
            if (bunny[cur_bunny] - T <= nest[cur_nest] && nest[cur_nest] <= bunny[cur_bunny] + T) {
                cur_bunny++;
                cnt++;
                res++;
            } else if (bunny[cur_bunny] + T < nest[cur_nest]) {
                cur_bunny++;
            } else {
                cur_nest++;
                cnt = 0;
            }
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
