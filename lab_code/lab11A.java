import java.io.*;
import java.util.*;

public class lab11A {
    public static class node {
        int val;
        ArrayList<node> nei = new ArrayList<>();
        ArrayList<Long> weight = new ArrayList<>();

        public node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        node[] tree = new node[N + 1];
        for (int i = 0; i <= N; i++) {
            tree[i] = new node(i);
        }
        for (int i = 1; i < N; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long w = in.nextInt();
            tree[u].nei.add(tree[v]);
            tree[v].nei.add(tree[u]);
            tree[u].weight.add(w);
            tree[v].weight.add(w);
        }

        long[] OPT0 = new long[N + 1];
        long[] OPT1 = new long[N + 1];
        Stack<node> s = new Stack<>();
        boolean[] isVisited = new boolean[N + 1];
        s.push(tree[1]);
        while (!s.isEmpty()) {
            node tmp = s.pop();
            isVisited[tmp.val] = true;
            int len = tmp.nei.size();
            for (int i = 0; i < len; i++) {
                node neighbour = tmp.nei.get(i);
                if (!isVisited[neighbour.val]) {
                    if ((neighbour.val != 1) && (neighbour.nei.size() == 1)) {
                        OPT1[tmp.val] = 0;
                        continue;
                    }
                    OPT0[tmp.val] = (OPT0[tmp.val] - OPT1[neighbour.val] + OPT0[neighbour.val]);
                    OPT1[tmp.val] = Math.max(OPT0[tmp.val], tmp.weight.get(i) + OPT0[tmp.val] - OPT1[neighbour.val] + OPT0[neighbour.val]);
                    s.push(neighbour);
                }
            }
        }


        out.print(OPT1[1]);

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
