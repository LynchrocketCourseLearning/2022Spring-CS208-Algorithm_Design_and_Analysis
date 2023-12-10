import java.io.*;
import java.util.*;

public class lab5A {
    public static class node {
        int key = 0;
        int value = 0;
        node pre = null;
        node next = null;

        public node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class Cache {
        HashMap<Integer, node> CacheMap = new HashMap<>();
        int capacity = 0;
        int cnt = 0; // judge if full of capacity
        node head;
        node tail;

        public Cache(int capacity) {
            this.capacity = capacity;

            this.head = new node(0,0);
            this.tail = new node(0,0);

            head.next = tail;
            tail.pre = head;
        }

        public int get(int key) {
            node tmp = CacheMap.get(key);
            if (tmp == null) {
                return -1;
            }

            // remove from the linkedlist
            tmp.pre.next = tmp.next;
            tmp.next.pre = tmp.pre;
            // add next to the head
            tmp.pre = head;
            tmp.next = head.next;
            head.next.pre = tmp;
            head.next = tmp;

            return tmp.value;
        }

        public void put(int key, int value) {
            node tmp = CacheMap.get(key);
            if (tmp == null) {
                node a = new node(key, value);
                // add next to the head
                a.pre = head;
                a.next = head.next;
                head.next.pre = a;
                head.next = a;

                CacheMap.put(key, a);
                cnt++;

                if (cnt > capacity) {
                    // remove the least recently used (key, value)
                    node out = tail.pre;
                    tail.pre.pre.next = tail;
                    tail.pre = tail.pre.pre;
                    CacheMap.remove(out.key);

                    // reduce the counter
                    cnt--;
                }
            } else {
                // update the value
                tmp.value = value;

                // remove from the linkedlist
                tmp.pre.next = tmp.next;
                tmp.next.pre = tmp.pre;

                // add next to the head
                tmp.pre = head;
                tmp.next = head.next;
                head.next.pre = tmp;
                head.next = tmp;
            }
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int N = in.nextInt();
        int M = in.nextInt();
        Cache cache = new Cache(N);
        for (int i = 1; i <= M; i++) {
            String s = in.next();
            if (s.equals("put")) {
                cache.put(in.nextInt(), in.nextInt());
            } else if (s.equals("get")) {
                out.println(cache.get(in.nextInt()));
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
