import java.io.*;
import java.util.*;

public class lab8B {
    public static class node {
        int l, r;
        int cnt;
        node ltree, rtree;

        public node(int l, int r, int cnt) {
            this.l = l;
            this.r = r;
            this.cnt = cnt;
        }
    }

    public static void build_first(node root, int l, int r) {
        if (l == r) return;
        int mid = (l + r) >> 1;
        root.ltree = new node(l, mid, 0);
        root.rtree = new node(mid + 1, r, 0);
        build_first(root.ltree, l, mid);
        build_first(root.rtree, mid + 1, r);
    }

    public static void build(node new_node, node pre_node, int lo, int hi, long val) {
        if (lo == hi) return;
        int mid = (lo + hi) >> 1;
        if (val <= mid) {
            new_node.ltree = new node(lo, mid, pre_node.ltree.cnt + 1);
            new_node.rtree = pre_node.rtree;
            build(new_node.ltree, pre_node.ltree, lo, mid, val);
        } else {
            new_node.rtree = new node(mid + 1, hi, pre_node.rtree.cnt + 1);
            new_node.ltree = pre_node.ltree;
            build(new_node.rtree, pre_node.rtree, mid + 1, hi, val);
        }
    }

    public static int query(node l_tree, node r_tree, int lo, int hi, int rank) {
        if (lo == hi) return lo;
        int mid = (lo + hi) >> 1;
        int diff = r_tree.ltree.cnt - l_tree.ltree.cnt;
        if (rank <= diff) return query(l_tree.ltree, r_tree.ltree, lo, mid, rank);
        else return query(l_tree.rtree, r_tree.rtree, mid + 1, hi, rank - diff);
    }

    public static int binary(Long[] val, int lo, int hi, long tar) {
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (tar == val[mid]) return mid;
            if (tar > val[mid]) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        int Q = in.nextInt();
        long[] a = new long[N + 1];
        Set<Long> set = new HashSet<>();
        set.add(0L);
        for (int i = 1; i <= N; i++) {
            a[i] = in.nextInt();
            set.add(a[i]);
        }
        Long[] val = new Long[set.size()];
        set.toArray(val);
        Arrays.sort(val);
        long[] b = new long[N + 1]; // 离散化后的结果，b[i]都对应着a[i]
        for (int i = 1; i <= N; i++) {
            if (b[i] != 0) continue;
            b[i] = binary(val, 1, val.length, a[i]);
        }

        int len = set.size();
        node[] root = new node[N + 1];
        root[0] = new node(1, len, 0);
        build_first(root[0], 1, len);
        for (int i = 1; i <= N; i++) {
            root[i] = new node(1, len, root[i - 1].cnt + 1);
            build(root[i], root[i - 1], 1, len, b[i]);
        }

        for (int i = 1; i <= Q; i++) {
            int L = in.nextInt();
            int R = in.nextInt();
            int index = query(root[L - 1], root[R], 1, len, ((R - L) >> 1) + 1);
            out.println(val[index]);
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
