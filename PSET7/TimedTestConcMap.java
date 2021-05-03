package Week12;

import Week11.BarrierTimer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class TimedTestConcMap extends AddRemoveTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedTestConcMap(int nPairThreads, int trials) {
        super(nPairThreads, trials);
        barrier = new CyclicBarrier(this.nPairThreads * 2 + 1, timer);
        this.map = new ConcurrentHashMap<>();
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairThreads; i++) {
                pool.submit(new Adder());
                pool.submit(new Remover());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairThreads * (long) nTrials);
            System.out.print("Throughput: " + nsPerItem + " ns/item");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws Exception {
        int tpt = 10000; // trials per thread
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedTestConcMap t = new TimedTestConcMap(pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        pool.shutdown();
    }
}
   