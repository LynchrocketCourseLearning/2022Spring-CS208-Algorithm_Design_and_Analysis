import java.io.*;
import java.util.*;

public class lab12B {
    static int S, T;
    static int idx = 0;
    static int[] level = new int[101000];
    static int[] cur = new int[201000];

    static int[] edge = new int[201000];
    static int[] next = new int[201000];
    static long[] capacity = new long[201000];
    static int[] head = new int[201000];

    public static void addEdge(int u, int v, long cap) {
        edge[idx] = v;
        capacity[idx] = cap;
        next[idx] = head[u];
        head[u] = idx++;
    }

    public static boolean bfs() {
        Queue<Integer> q = new LinkedList<>();
        Arrays.fill(level, -1);
        q.offer(S);
        level[S] = 0;
        cur[S] = head[S];
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int i = head[u]; i != -1; i = next[i]) {
                int v = edge[i];
                if (level[v] == -1 && capacity[i] > 0) {
                    level[v] = level[u] + 1;
                    cur[v] = head[v];
                    if (v == T) return true;
                    q.offer(v);
                }
            }
        }
        return false;
    }

    public static long dfs(int u, long residual) {
        if (u == T) return residual;
        long flow = 0;
        for (int i = cur[u]; i != -1; i = next[i]) {
            cur[u] = i;
            int v = edge[i];
            if (level[u] + 1 == level[v] && capacity[i] > 0) {
                long d = dfs(v, Math.min(capacity[i], residual - flow));
                capacity[i] -= d;
                capacity[i ^ 1] += d;
                flow += d;
                if (flow == residual) return flow;
            }
        }
        return flow;
    }

    public static class block {
        int col;
        int row;
        int val;
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int t = in.nextInt();
        for (int i = 1; i <= t; i++) {
            int N = in.nextInt();
            int M = in.nextInt();
            int A = in.nextInt();
            int B = in.nextInt();

            Arrays.fill(head, -1);
            Arrays.fill(cur, -1);


            block[][] blo = new block[N + 1][M + 1];
            for (int j = 1; j <= N; j++) {
                for (int k = 1; k <= M; k++) {
                    blo[j][k] = new block();
                }
            }

            int cnt = 2;
            for (int j = 1; j <= N; j++) {
                String l = in.next();
                for (int k = 1; k <= M; k++) {
                    blo[j][k].val = l.charAt(k - 1) - '0';
                    if (blo[j][k].val == 0) {
                        blo[j][k].col = cnt;
                        cnt++;
                        blo[j][k].row = cnt;
                        cnt++;
                        addEdge(blo[j][k].col, blo[j][k].row, 1);
                        addEdge(blo[j][k].row, blo[j][k].col, 1);
                        if (j != 1 && blo[j - 1][k].val == 0) {
                            addEdge(blo[j][k].row, blo[j - 1][k].row, 1);
                            addEdge(blo[j - 1][k].row, blo[j][k].row, 1);
                        }
                        if (k != 1 && blo[j][k - 1].val == 0) {
                            addEdge(blo[j][k].col, blo[j][k - 1].col, 1);
                            addEdge(blo[j][k - 1].col, blo[j][k].col, 1);
                        }
                    }
                }
            }

            S = 1;
            for (int j = 1; j <= A; j++) {
                int v = in.nextInt();
                if (blo[1][v].val == 0) {
                    addEdge(S, blo[1][v].col, 1);
                    addEdge(blo[1][v].col, S, 0);
                }
            }

            T = cnt;
            for (int j = 1; j <= B; j++) {
                int u = in.nextInt();
                if (blo[N][u].val == 0) {
                    addEdge(blo[N][u].col, T, 1);
                    addEdge(T, blo[N][u].col, 0);
                }
            }

            long res = 0;
            while (bfs()) {
                res += dfs(S, 2147483648L);
            }
            if (res >= A)
                out.println("Yes");
            else
                out.println("No");

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
