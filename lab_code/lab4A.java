import java.io.*;
import java.util.*;

public class lab4A {
    public static class node {
        long h;
        long a;
        long b;
        long c;
        ArrayList<node> nei = new ArrayList<>();
        long indegree = 0;
        long outdegree = 0;

        long CE = 0;
        long CE_mod = 0;
        double CE_log = 0;

        public node(long h, long a, long b, long c) {
            this.h = h;
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        int M = in.nextInt();
        long C = in.nextLong();
        node[] graph = new node[N + 1];
        for (int i = 1; i <= N; i++) {
            long h = in.nextLong();
            long a = in.nextLong();
            long b = in.nextLong();
            long c = in.nextLong();
            graph[i] = new node(h, a, b, c);
        }
        for (int i = 1; i <= M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            graph[x].nei.add(graph[y]);
            graph[y].indegree++;
            graph[x].outdegree++;
        }
        Deque<node> q = new ArrayDeque<>();
        q.offer(graph[1]);
        graph[1].CE = C;
        long res_mod = -1;
        double res_log = 0;
        while (!q.isEmpty()) {
            node e = q.poll();
            if (e.CE >= 1000000000) {
                e.CE_log = e.CE_log + Math.log10(e.b);
                e.CE_mod = (e.CE_mod % 1000000007 * e.b % 1000000007) % 1000000007;
            } else if (e.CE >= e.h) {
                e.CE = Math.max(Math.max(e.CE + e.a, e.CE * e.b), e.c);
                e.CE_log = Math.log10(e.CE);
                e.CE_mod = e.CE % 1000000007;
            } else {
                for (node c : e.nei) {
                    c.indegree--;
                    if (c.indegree == 0) q.offer(c);
                }
                continue;
            }
            if (e.outdegree == 0) {
                if (e.CE_log > res_log) {
                    res_mod = e.CE_mod;
                    res_log = e.CE_log;
                }
            }
            for (node c : e.nei) {
                c.indegree--;
                if (e.CE_log > c.CE_log) {
                    c.CE = e.CE;
                    c.CE_mod = e.CE_mod;
                    c.CE_log = e.CE_log;
                }
                if (c.indegree == 0) q.offer(c);
            }
        }

        out.print(res_mod);

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
