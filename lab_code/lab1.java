import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Map<String, Integer> manMap = new HashMap<>();
        Map<String, Integer> womanMap = new HashMap<>();
        String[] man = new String[N];
        String[] woman = new String[N];

        for (int i = 0; i < N; i++) {
            String tmp = sc.next();
            man[i] = tmp;
            manMap.put(tmp, i);
        }
        for (int i = 0; i < N; i++) {
            String tmp = sc.next();
            woman[i] = tmp;
            womanMap.put(tmp, i);
        }

        int[][] manList = new int[N][N]; // man prefer list
        int[][] womanList = new int[N][N]; // woman prefer list

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String tmp = sc.next();
                manList[i][j] = womanMap.get(tmp);
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String tmp = sc.next();
                womanList[i][j] = manMap.get(tmp);
            }
        }

        int[][] inverseWomanList = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                inverseWomanList[i][womanList[i][j]] = j;
            }
        }

        int[] wife = new int[N]; // man's wife
        int[] husband = new int[N]; // woman's husband
        for (int i = 0; i < N; i++) {
            husband[i] = -1;
        }

        Queue<Integer> freeMan = new LinkedList<>(); // have not engaged man
        for (int i = 0; i < N; i++) {
            freeMan.add(i);
        }

        int[] cur = new int[N];
        while (!freeMan.isEmpty()) { // still exists free man
            int m = freeMan.peek();
            int w = manList[m][cur[m]];
            cur[m]++;

            if (husband[w] == -1) {
                freeMan.remove();
                wife[m] = w;
                husband[w] = m;
            } else {
                if (inverseWomanList[w][husband[w]] > inverseWomanList[w][m]) {
                    int prevMan = husband[w];
                    husband[w] = m;
                    wife[m] = w;
                    freeMan.remove();
                    freeMan.add(prevMan);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            System.out.println(man[i] + " " + woman[wife[i]]);
        }
    }
}
