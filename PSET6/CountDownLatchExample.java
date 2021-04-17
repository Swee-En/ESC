package Week11;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountDownLatchExample {

    public static void main(String args[]) {
        final CountDownLatch latch = new CountDownLatch(6);
        Thread cacheService = new Thread(new Service("CacheService", 1000, latch));
        Thread alertService = new Thread(new Service("AlertService", 1000, latch));
        Thread validationService = new Thread(new Service("ValidationService", 1000, latch));

        cacheService.start(); //separate thread will initialize CacheService
        alertService.start(); //another thread for AlertService initialization
        validationService.start();

        try {
            latch.await();  //main thread is waiting on CountDownLatch to finish
            Thread.sleep(1000);
            System.out.println("All services are up, Application is starting now");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

class Service implements Runnable {
    private final String name;
    private final int timeToStart;
    private final CountDownLatch latch;

    public Service(String name, int timeToStart, CountDownLatch latch) {
        this.name = name;
        this.timeToStart = timeToStart;
        this.latch = latch;
    }

    public void run() {
        try {
            Thread.sleep(timeToStart);
        } catch (InterruptedException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(name + " is Up");
        //doing something here.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Decrements the count of the latch, releasing all waiting threads if the count reaches zero.
        latch.countDown();
        System.out.println(name + " has passed countdown once");

        //doing something here.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown(); //reduce count of CountDownLatch by 1
        System.out.println(name + " has passed countdown twice");

    }
}
