import java.io.*;
import java.util.*;

public class lab5B {
    public static class edge {
        int st, ed;
        long cost;
        long passCount = 0;

        public edge(int st, int ed, long cost) {
            this.st = st;
            this.ed = ed;
            this.cost = cost;
        }
    }

    public static class node {
        int num;
        long cost;

        public node(int num, long cost) {
            this.num = num;
            this.cost = cost;
        }
    }

    public static class pair {
        int index;
        long weight;

        public pair(int index, long weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        int M = in.nextInt();
//        node[] nodes = new node[N + 1];
//        for (int i = 1; i <= N; i++) {
//            nodes[i] = new node(i, 10000000010L);
//        }
        edge[] edges = new edge[M + 1];
        pair[][] graph = new pair[N + 1][N + 1];
        for (int i = 1; i <= M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            long w = in.nextInt();
            if (graph[x][y] == null || graph[x][y].weight > w) {
                graph[x][y] = graph[y][x] = new pair(i, w);
                edges[i] = new edge(x, y, w);
            } else if (graph[x][y].weight == w) {
                edges[i] = edges[graph[x][y].index];
            } else {
                edges[i] = new edge(x, y, w);
            }
        }
        int P = in.nextInt();
        for (int i = 1; i <= P; i++) {
            int start = in.nextInt();
            int end = in.nextInt();
            PriorityQueue<node> q = new PriorityQueue<>((o1, o2) -> (int) (o1.cost - o2.cost));
            long[] dij = new long[N + 1];
            Arrays.fill(dij, 10000000010L);
            boolean[] visited_dij = new boolean[N + 1];
            dij[start] = 0;
            q.offer(new node(start, 0));
            while (!q.isEmpty()) {
                node tmp = q.poll();
                if (tmp.num == end) break;
                if (!visited_dij[tmp.num]) {
                    visited_dij[tmp.num] = true;
                    for (int j = 1; j <= N; j++) {
                        if (graph[tmp.num][j] != null && !visited_dij[j]) {
                            long cost = tmp.cost + graph[tmp.num][j].weight;
                            if (dij[j] > cost) {
                                dij[j] = cost;
                                q.offer(new node(j, dij[j]));
                            }
                        }
                    }
                }
            }
            Queue<node> b = new LinkedList<>();
            boolean[] visited_bfs = new boolean[N + 1];
            b.offer(new node(end, 0));
            while (!b.isEmpty()) {
                node tmp = b.poll();
                for (int j = 1; j <= N; j++) {
                    if (graph[tmp.num][j] == null) continue;
                    pair t = graph[j][tmp.num];
                    if (dij[j] + t.weight == dij[tmp.num]) {
                        edges[t.index].passCount++;
                        if (!visited_bfs[j]) {
                            visited_bfs[j] = true;
                            b.offer(new node(j, dij[j]));
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= M; i++) {
            out.println(edges[i].passCount);
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
