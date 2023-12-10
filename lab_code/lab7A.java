import java.io.*;
import java.util.*;

// G[i] = B[i]^B[i+1]
public class lab7A {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        long N = in.nextLong();
        int len = (int) (Math.log(N + 1) / Math.log(2) + 1);
        boolean[] Binary = new boolean[len];
        int cnt = len - 1;
        // to binary
        while (N != 0) {
            Binary[cnt] = (N % 2 == 1);
            N >>= 1;
            cnt--;
        }
        // to Gray
        if (Binary[0]) out.print(1);
        for (int i = 0; i < len-1; i++) {
            if (Binary[i] ^ Binary[i + 1]) out.print(1);
            else out.print(0);
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
