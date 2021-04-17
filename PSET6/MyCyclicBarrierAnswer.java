package Week11;

/**
 * Question of Cohort Exercise 1
 */

//hint 2: we must ensure this class to be thread-safe. why? and how?
public class MyCyclicBarrierAnswer {
    private int count = 0;
    private Runnable torun;//hint 3: when should we call this runable task?

    public MyCyclicBarrier(int count, Runnable torun) {
        this.count = count;
        this.torun = torun;
    }

    public MyCyclicBarrier(int count) {
        this.count = count;
    }

    //TODO: complete the implementation below.
    //hint 1: use wait(), notifyAll()
    //precondition:
    //postcondition:
    public synchronized void await() throws InterruptedException {
        count--;
        if (count == 0){
            torun.run();
            notifyAll();
        }

        else{
            wait();
        }
    }
}
