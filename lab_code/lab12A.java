import java.io.*;
import java.util.*;

public class lab12A {
    static int N, M, S, T;
    static int idx = 0;
    static int[] level = new int[10100];
    static int[] cur = new int[20100];

    static int[] edge = new int[20100];
    static int[] next = new int[20100];
    static long[] capacity = new long[20100];
    static int[] head = new int[20100];

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

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        N = in.nextInt();
        M = in.nextInt();
        S = in.nextInt();
        T = in.nextInt();

        Arrays.fill(head, -1);
        Arrays.fill(cur, -1);

        for (int i = 1; i <= M; i++) {
            int u, v, c;
            u = in.nextInt();
            v = in.nextInt();
            c = in.nextInt();
            addEdge(u, v, c);
            addEdge(v, u, 0);
        }

        long res = 0;
        while (bfs()) {
            res += dfs(S, 2147483648L);
        }

        out.println(res);

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
