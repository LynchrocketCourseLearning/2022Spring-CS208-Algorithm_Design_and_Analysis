import java.io.*;
import java.util.*;

public class lab4B {
    public static class interval {
        long min_inter = 0;
        int index_inter = 0;

        int st = 0;
        int end = 0;

        long sum = 0;
        long base_val = 0;

        public interval(int st, int end, long sum, long base_val) {
            this.st = st;
            this.end = end;
            this.sum = sum;
            this.base_val = base_val;
        }
    }

    public static class node {
        long val = 0;
        int index = 0;

        public node(long val, int index) {
            this.val = val;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        long M = in.nextLong();

        // preprocessing
        int[] logn = new int[N + 1];
        logn[1] = 0;
        logn[2] = 1;
        for (int i = 3; i <= N; i++) {
            logn[i] = logn[i / 2] + 1;
        }

        // construct st
        int len = logn[N] + 1;
        node[][] min_a = new node[N + 1][len];
        for (int i = 1; i <= N; i++) {
            min_a[i][0] = new node(in.nextLong(), i);
        }
        for (int j = 1; j <= len; j++) {
            for (int i = 1; i + (1 << j) - 1 <= N; i++) {
                if (min_a[i][j - 1].val < min_a[i + (1 << (j - 1))][j - 1].val) {
                    min_a[i][j] = min_a[i][j - 1];
                } else {
                    min_a[i][j] = min_a[i + (1 << (j - 1))][j - 1];
                }
            }
        }

        // prefix sum
        long[] sum = new long[N + 1];
        sum[0] = 0;
        for (int i = 1; i <= N; i++) {
            sum[i] = sum[i - 1] + in.nextLong();
        }

        interval init = new interval(1, N, sum[N] - sum[0], 0);
        if (min_a[1][logn[N]].val < min_a[N - (1 << logn[N]) + 1][logn[N]].val) {
            init.min_inter = min_a[1][logn[N]].val;
            init.index_inter = min_a[1][logn[N]].index;
        } else {
            init.min_inter = min_a[N - (1 << logn[N]) + 1][logn[N]].val;
            init.index_inter = min_a[N - (1 << logn[N]) + 1][logn[N]].index;
        }
        long income = init.min_inter * init.sum;
        long time = init.min_inter;

        Comparator<interval> cmp = (o1, o2) -> (int) (o2.sum - o1.sum);
        Queue<interval> q = new PriorityQueue<>(cmp);
        if (init.index_inter > init.st)
            q.offer(new interval(init.st, init.index_inter - 1, sum[init.index_inter - 1] - sum[init.st - 1], init.min_inter));
        if (init.index_inter < init.end)
            q.offer(new interval(init.index_inter + 1, init.end, sum[init.end] - sum[init.index_inter], init.min_inter));
        while (!q.isEmpty() && time < M) {
            interval tmp = q.poll();
            int l = tmp.st;
            int r = tmp.end;
            int s = logn[r - l + 1];
            if (min_a[l][s].val < min_a[r - (1 << s) + 1][s].val) {
                tmp.min_inter = min_a[l][s].val;
                tmp.index_inter = min_a[l][s].index;
            } else {
                tmp.min_inter = min_a[r - (1 << s) + 1][s].val;
                tmp.index_inter = min_a[r - (1 << s) + 1][s].index;
            }
            if (time + (tmp.min_inter - tmp.base_val) <= M) {
                income += (tmp.min_inter - tmp.base_val) * tmp.sum;
                time += (tmp.min_inter - tmp.base_val);
            } else {
                income += (M - time) * tmp.sum;
                time = M;
                break;
            }
            if (tmp.index_inter > tmp.st)
                q.offer(new interval(tmp.st, tmp.index_inter - 1, sum[tmp.index_inter - 1] - sum[tmp.st - 1], tmp.min_inter));
            if (tmp.index_inter < tmp.end)
                q.offer(new interval(tmp.index_inter + 1, tmp.end, sum[tmp.end] - sum[tmp.index_inter], tmp.min_inter));
        }

        out.print(income);

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
