import java.io.*;
import java.util.*;

public class lab3A {
    public static class node {
        int val;
        long indegree = 0;
        long outdegree = 0;
        ArrayList<node> in = new ArrayList<>();
        ArrayList<node> out = new ArrayList<>();
        long chain = 0;
        long rev_chain = 0;

        public node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        int M = in.nextInt();
        if (M == 0) {
            for (int i = 1; i <= N; i++) {
                out.print("1 ");
            }
        } else {
            node[] list = new node[N + 1];
            for (int i = 1; i <= N; i++) {
                list[i] = new node(i);
            }
            for (int i = 1; i <= M; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                list[x].out.add(list[y]);
                list[x].outdegree++;
                list[y].in.add(list[x]);
                list[y].indegree++;
            }

            Deque<node> q_in = new ArrayDeque<>();
            Deque<node> q_out = new ArrayDeque<>();
            for (int i = 1; i <= N; i++) {
                node a = list[i];
                if (a.indegree == 0) {
                    a.chain = 1;
                    q_in.offer(a);
                }
                if (a.outdegree == 0) {
                    a.rev_chain = 1;
                    q_out.offer(a);
                }
            }
            while (!q_in.isEmpty()) {
                node e = q_in.poll();
                for (node c : e.out) {
                    c.indegree--;
                    c.chain = (c.chain % 1000000007 + e.chain % 1000000007) % 1000000007;
                    if (c.indegree == 0) q_in.offer(c);
                }
            }
            while (!q_out.isEmpty()) {
                node e = q_out.poll();
                for (node c : e.in) {
                    c.outdegree--;
                    c.rev_chain = (c.rev_chain % 1000000007 + e.rev_chain % 1000000007) % 1000000007;
                    if (c.outdegree == 0) q_out.offer(c);
                }
            }
            for (int i = 1; i <= N; i++) {
                out.print((list[i].chain % 1000000007) * (list[i].rev_chain % 1000000007) % 1000000007 + " ");
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
