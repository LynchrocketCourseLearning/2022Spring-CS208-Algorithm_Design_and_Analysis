import java.io.*;
import java.util.*;
// not finished
public class lab6A {
    static int[] edge = new int[200100];
    static int[] next = new int[20100];
    static int[] head = new int[100100];
    static int idx = 0;

    public static void addEdge(int u, int v) {
        edge[idx] = v;
        next[idx] = head[u];
        head[u] = idx++;
    }

    static int[][] dp = new int[100100][100];
    static int[] level = new int[100100];

    static void dfs(int u, int v) {
        level[u] = level[v] + 1;
        dp[u][0] = v;
        for (int i = 1; (1<<i) <= level[u]; i++) {
            dp[u][i] = dp[dp[u][i - 1]][i - 1];
        }
        for (int i = head[u]; i != 0; i = next[i]) {
            if (edge[i] != v) {
                dfs(edge[i], u);
            }
        }
    }

    static int find(int u, int v) {
        if (level[u] > level[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        for (int i = 99; i >= 0; i--) {
            if (level[u] <= level[v] - (1 << i)) {
                v = dp[v][i];
            }
        }
        if (u == v) return u;
        for (int i = 99; i >= 0; i--) {
            if (dp[u][i] != dp[v][i]) {
                u = dp[u][i];
                v = dp[v][i];
            }
        }
        return dp[u][0];
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        for (int i = 1; i < N; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs(1, 0);
        int Q = in.nextInt();
        for (int i = 1; i <= Q; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int res = find(x, y);
            out.println(res);
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
