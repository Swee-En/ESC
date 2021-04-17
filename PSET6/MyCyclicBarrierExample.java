package Week11;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyCyclicBarrierExample {

    private static class Task implements Runnable {
        private MyCyclicBarrier barrier;

        public Task(MyCyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            try {
                for (int i = 0; i < 2; i++) {
                    System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
                }
            } catch (Exception ex) {
                Logger.getLogger(MyCyclicBarrierExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) {
        //A CyclicBarrier supports an optional Runnable command
        final MyCyclicBarrier cb = new MyCyclicBarrier(3, new Runnable() {
            public void run() {
                //This task will be executed once all thread reaches barrier
                System.out.println("All parties are arrived at barrier, lets play");
            }
        });

        //starting each of thread
        Thread t1 = new Thread(new Task(cb), "Thread 1");
        Thread t2 = new Thread(new Task(cb), "Thread 2");
        Thread t3 = new Thread(new Task(cb), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}