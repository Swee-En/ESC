import java.util.concurrent.CountDownLatch;

/**
 * Question of Cohort Exercise 2
 */

public class CountDownLatchExerciseQns {

    public static void main(String args[]) throws InterruptedException {
        int limit = 7;
        final int noOfSearcher = 4;
        final CountDownLatch latch = new CountDownLatch(limit);//this is used as the recorder/ledger.
        final CountDownLatch finish = new CountDownLatch(noOfSearcher);//this is used as the sequence controller.
        String[] array = new String[]{"A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D", "F", "F"};

        final Searcher[] searchers = new Searcher[noOfSearcher];

        //creating all the searcher threads and start them to search "F".
        for (int i = 0; i < noOfSearcher; i++) {
            searchers[i] = new Searcher(array, i * array.length / noOfSearcher, (i + 1) * array.length / noOfSearcher, latch, finish);
            searchers[i].start();
        }

        //creating the awaitThread to wait for searchers to finish searching.
        Thread awaitThread = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("awaitThread awaiting");
                    latch.await();//What is the purpose of this wait? What is being waiting for?
                    System.out.println("awaitThread finishing");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  //main thread is waiting on CountDownLatch to finish

                //force all searchers to stop now..
                for (int i = 0; i < noOfSearcher; i++) {
                    System.out.print("Interrupted");
                    searchers[i].interrupt();
                }
                //force all searchers to stop now..
                while (finish.getCount() > 0) {
                    finish.countDown();
                }
            }
        }
        );
        awaitThread.start();
        System.out.println("main Thread awaiting");
        finish.await();
        System.out.println("main Thread finishing");
        while (latch.getCount() > 0) {
            latch.countDown();
        }
    }
}

class Searcher extends Thread {
    private final String[] data;
    private final int start;
    private final int end;
    private final CountDownLatch latch;
    private final CountDownLatch finish;

    public Searcher(String[] data, int start, int end, CountDownLatch latch, CountDownLatch finish) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.latch = latch;
        this.finish = finish;
    }

    /**
     * TODO: fill up this method.
     * check for "F".
     */
    public void run() {
        int n = data.length;
        int counting = 0;

        for (int i = 0; i < n; i++){
            if (data[i] == "F"){
                counting++;
            }
        }
        System.out.println("The number of 'F's is: " + counting);

        latch.countDown();
        finish.countDown();
    }
}
