import java.math.BigInteger;
import java.util.ArrayList;

public class FactorThread {
    public static void main(String[] args) {

        BigInteger n = new BigInteger("4294967297");
        ArrayList<Threadt> l1 = new ArrayList<Threadt>();
        boolean repeat = true;

        Threadt thread1 = new Threadt(n);
        Threadt thread2 = new Threadt(n);
        Threadt thread3 = new Threadt(n);

        l1.add(thread1);
        l1.add(thread2);
        l1.add(thread3);

        thread1.start();
        thread2.start();
        thread3.start();

        while (repeat) {
            for (Threadt l : l1) {
                if (l.getValue() == null) {
                    continue;
                } else {
                    System.out.println("The factor is: " + l.getValue());
                    repeat = false;
                    break;
                }
            }

        }

        for (Threadt t : l1) {
            t.interrupt();
            if (t.isInterrupted()) {
                System.out.println(t + " is interrupted.");
            }
        }
    }

    // Precondition: n is a semi-prime number.
    // Postcondition: the returned value is a prime factor of n;
    public static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");// begin the guess from "2"
        BigInteger zero = new BigInteger("0");
        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }
            i = i.add(new BigInteger("1"));
        }

        assert (false); // if this is reached, an error occurs.
        return null;
    }
}

class Threadt extends Thread {
    private BigInteger n, value;

    public Threadt(BigInteger n) {
        this.n = n;
    }

    public void run() {
        value = FactorThread.factor(n);
    }

    public BigInteger getValue() {
        return value;
    }
}
