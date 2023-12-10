import java.io.*;
import java.util.*;

public class lab8A {
    public static class complex {
        double re;
        double im = 0;

        public complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public complex(double re) {
            this.re = re;
        }

        public double norm() {
            return Math.sqrt(this.re * this.re + this.im * this.im);
        }

        public complex add(complex b) {
            return new complex(this.re + b.re, this.im + b.im);
        }

        public complex sub(complex b) {
            return new complex(this.re - b.re, this.im - b.im);
        }

        public complex mul(complex b) {
            return new complex(this.re * b.re - this.im * b.im, this.im * b.re + this.re * b.im);
        }
    }

    static void FFT(int n, complex[] a) {
        if (n == 1) return;
        complex[] e = new complex[n >> 1];
        complex[] d = new complex[n >> 1];
        for (int i = 0; i < n; i += 2) {
            e[i >> 1] = a[i];
            d[i >> 1] = a[i + 1];
        }
        FFT(n >> 1, e);
        FFT(n >> 1, d);
        int len = n >> 1;
        complex w = new complex(Math.cos(2 * Math.PI / n), -Math.sin(2 * Math.PI / n));
        complex wk = new complex(1, 0);
        for (int i = 0; i < len; i++) {
            complex tmp = wk.mul(d[i]);
            a[i] = e[i].add(tmp);
            a[i + (n >> 1)] = e[i].sub(tmp);
            wk = w.mul(wk);
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int N = in.nextInt();
        int cnt = 1 << N;
        complex[] x = new complex[cnt];
        for (int i = 0; i < cnt; i++) {
            x[i] = new complex(Double.parseDouble(in.next()), 0);
        }
        FFT(cnt, x);
        for (int i = 0; i < cnt; i++) {
            out.println(x[i].norm());
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
