package Week11;

import java.util.concurrent.Phaser;

public class PhaserExample {
    int synchronize_phases = 2;
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser();
        phaser.register();//register self... phaser waiting for 1 party (thread)
        System.out.println(phaser.getRegisteredParties() + " have registered.");
        System.out.println("Phasecount is " + phaser.getPhase());
        new PhaserExample().testPhaser(phaser, 2000);//phaser waiting for 2 parties
        new PhaserExample().testPhaser(phaser, 4000);//phaser waiting for 3 parties
        new PhaserExample().testPhaser(phaser, 6000);//phaser waiting for 4 parties
        //now that all threads are initiated
        phaser.arriveAndDeregister();//we will de-register main thread so that the barrier condition of 3 thread arrival is meet.
//        phaser.arriveAndAwaitAdvance();//Arrives at this phaser and awaits others.
        System.out.println(phaser.getRegisteredParties() + " have registered.");
        Thread.sleep(10000);
        System.out.println("Phasecount is " + phaser.getPhase());
    }

    private void testPhaser(final Phaser phaser, final int sleepTime) {
        phaser.register();//adds a new unarrived party to this phaser.
        System.out.println(phaser.getRegisteredParties() + " have registered.");
        new Thread(() -> {
            try {
                for (int i = 0; i < synchronize_phases; i++) {
                    System.out.println(Thread.currentThread().getName() + " arrived");
                    phaser.arriveAndAwaitAdvance();//Arrives at this phaser and awaits others.
//                    phaser.arrive(); // What if we call arrive?
                    System.out.println(Thread.currentThread().getName()+" pass arrived");
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " after passing barrier");
        }).start();
    }
}