import java.util.Locale;

public class Main {
    private final static int M = 64;
    private final static int N = 60;
    private final static int K = 32;
    private final static int[][] a = new int[M][K];
    private final static int[][] b = new int[K][N];
    private final static int[][] c = new int[M][N];

    public static void mmul() {
        CPUImpl cpuWithLRU = new CPUImpl(new LRUCache(
                Config.CACHE_SETS_COUNT.value,
                Config.CACHE_WAY.value,
                Config.CACHE_LINE_SIZE.value
        ));

        CPUImpl cpuWithPLRU = new CPUImpl(new PLRUCache(
                Config.CACHE_SETS_COUNT.value,
                Config.CACHE_WAY.value,
                Config.CACHE_LINE_SIZE.value
        ));

        int pa = 0; //pa = a
        cpuWithLRU.createLocal();
        cpuWithPLRU.createLocal();

        int pc = 0; //pc = c
        cpuWithLRU.createLocal();
        cpuWithPLRU.createLocal();

        cpuWithLRU.createLocal();
        cpuWithPLRU.createLocal();
        for (int y = 0; y < M; y++) {
            cpuWithLRU.createLocal();
            cpuWithPLRU.createLocal();
            for (int x = 0; x < N; x++) {

                int pb = 0; // pb = b
                cpuWithLRU.createLocal();
                cpuWithPLRU.createLocal();

                int s = 0;
                cpuWithLRU.createLocal();
                cpuWithPLRU.createLocal();

                cpuWithLRU.createLocal();
                cpuWithPLRU.createLocal();
                for (int k = 0; k < K; k++) {
                    s += a[pa][k] * b[pb][x]; //s += pa[k] * pb[x]
                    cpuWithLRU.C1_READ8(262144 + pa * K + k);
                    cpuWithPLRU.C1_READ8(262144 + pa * K + k);

                    cpuWithLRU.C1_READ16(264192 + pb * N * 2 + x * 2);
                    cpuWithPLRU.C1_READ16(264192 + pb * N * 2 + x * 2);
                    cpuWithLRU.multiplication();
                    cpuWithPLRU.multiplication();

                    cpuWithLRU.sum();
                    cpuWithPLRU.sum();

                    pb += 1; //pb += N
                    cpuWithLRU.incrementLocal();
                    cpuWithPLRU.incrementLocal();

                    cpuWithLRU.incrementLocal();
                    cpuWithPLRU.incrementLocal();

                    cpuWithLRU.nextIteration();
                    cpuWithPLRU.nextIteration();
                }

                c[pc][x] = s; //pc[x] = s
                cpuWithLRU.C1_WRITE32(268032 + pc * N * 4 + x * 4);
                cpuWithPLRU.C1_WRITE32(268032 + pc * N * 4 + x * 4);

                cpuWithLRU.incrementLocal();
                cpuWithPLRU.incrementLocal();

                cpuWithLRU.nextIteration();
                cpuWithPLRU.nextIteration();
            }
            pa += 1; //pa += K
            cpuWithLRU.incrementLocal();
            cpuWithPLRU.incrementLocal();

            pc += 1; //pc += N
            cpuWithLRU.incrementLocal();
            cpuWithPLRU.incrementLocal();

            cpuWithLRU.incrementLocal();
            cpuWithPLRU.incrementLocal();

            cpuWithLRU.nextIteration();
            cpuWithPLRU.nextIteration();
        }

        cpuWithLRU.exitFunction();
        cpuWithPLRU.exitFunction();

        System.out.printf(Locale.ENGLISH, "LRU:\thit perc. %3.4f%%\ttime: %d\npLRU:\thit perc. %3.4f%%\ttime: %d\n",
                cpuWithLRU.getPercentage() * 100, cpuWithLRU.getTicks(),
                cpuWithPLRU.getPercentage() * 100, cpuWithPLRU.getTicks());
    }

    public static void main(String[] args) {
        mmul();
    }
}
