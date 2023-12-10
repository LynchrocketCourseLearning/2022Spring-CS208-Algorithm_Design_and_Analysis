import java.io.*;
import java.util.*;

public class lab2B {
    public static class node {
        int val;
        boolean isVisited = false;
        long time = 0;
        int[] nei = new int[3];
        int ind_nei = 0;

        public node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        node[] graph = new node[N + 1];
        for (int i = 1; i <= N; i++) {
            if (graph[i] == null) graph[i] = new node(i);
            if (i != 1) graph[i].nei[graph[i].ind_nei++] = i - 1;
            if (i != N) graph[i].nei[graph[i].ind_nei++] = i + 1;
            graph[i].nei[graph[i].ind_nei++] = in.nextInt();
        }
        Queue<node> q = new ArrayDeque<>();
        q.offer(graph[1]);
        graph[1].isVisited = true;
        long[] time = new long[N + 1];
        time[1] = 0;
        while (!q.isEmpty()) {
            node tmp = q.poll();
            time[tmp.val] = tmp.time;
            for (int i = 0; i < tmp.ind_nei; i++) {
                node tmp2 = graph[tmp.nei[i]];
                if (!tmp2.isVisited) {
                    q.offer(tmp2);
                    tmp2.time = tmp.time + 1;
                    tmp2.isVisited = true;
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            out.print(time[i] + " ");
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
