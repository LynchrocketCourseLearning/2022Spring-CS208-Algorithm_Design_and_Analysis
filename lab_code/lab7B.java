import java.io.*;
import java.util.*;

public class lab7B {
    public static class node {
        long val;
        ArrayList<Integer> query = new ArrayList<>();

        public node(long val) {
            this.val = val;
        }
    }

    public static class interval {
        long val = -10000000000L;
        int left;
        int right;

        public interval(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    static node[] s;
    static interval[] res;
    static long[] max;
    static long[] min;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        s = new node[N + 1];
        max = new long[N + 1];
        min = new long[N + 1];
        s[0] = new node(0);
        for (int i = 1; i <= N; i++) {
            long val = in.nextLong();
            s[i] = new node(s[i - 1].val + val);
        }
        int Q = in.nextInt();
        res = new interval[Q + 1];
        for (int i = 1; i <= Q; i++) {
            int left = in.nextInt();
            int right = in.nextInt();
            s[left].query.add(i);
            s[right].query.add(i);
            res[i] = new interval(left, right);
        }

        divide_search(1, N);

        for (int i = 1; i <= Q; i++) {
            out.println(res[i].val);
        }

        out.close();
    }

    public static long divide_search(int l, int r) {
        if (l == r) {
            for (int qnum : s[l].query) {
                int ql = res[qnum].left;
                int qr = res[qnum].right;
                if (ql >= l || qr <= r)
                    res[qnum].val = Math.max(res[qnum].val, s[r].val - s[l - 1].val);
            }
            min[l] = max[r] = s[l].val;
            return s[r].val - s[l - 1].val;
        }
        int mid = (l + r) >> 1;
        long left_result = divide_search(l, mid);
        long right_result = divide_search(mid + 1, r);

        if (r - l >= 2) {
            min[mid] = s[mid].val;
            for (int i = mid - 1; i >= l - 1; i--) {
                min[i] = Math.min(min[i + 1], s[i].val);
            }
            max[mid + 1] = s[mid + 1].val;
            for (int i = mid + 2; i <= r; i++) {
                max[i] = Math.max(max[i - 1], s[i].val);
            }
        }
        long best = Math.max(max[r] - min[l - 1], Math.max(right_result, left_result));
        for (int i = l; i <= mid; i++) {
            for (int qnum : s[i].query) {
                int ql = res[qnum].left;
                int qr = res[qnum].right;
                if (qr >= r) {
                    res[qnum].val = Math.max(res[qnum].val, Math.max(right_result, max[r] - min[ql - 1]));
                    if (ql == l) {
                        res[qnum].val = Math.max(res[qnum].val, best);
                    }
                } else if (qr > mid) {
                    res[qnum].val = Math.max(res[qnum].val, max[qr] - min[ql - 1]);
                }
            }
        }
        for (int i = mid + 1; i <= r; i++) {
            for (int qnum : s[i].query) {
                int ql = res[qnum].left;
                int qr = res[qnum].right;
                if (ql <= l) {
                    res[qnum].val = Math.max(res[qnum].val, Math.max(left_result, max[qr] - min[l - 1]));
                    if (ql == r) {
                        res[qnum].val = Math.max(res[qnum].val, best);
                    }
                } else if (ql < mid) {
                    res[qnum].val = Math.max(res[qnum].val, max[qr] - min[ql - 1]);
                }
            }
        }
        return best;
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
