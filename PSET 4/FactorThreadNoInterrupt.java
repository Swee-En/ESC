import java.math.BigInteger;
import java.util.ArrayList;

public class FactorThreadNoInterrupt {
    public static void main (String[] args){
        
        BigInteger n = new BigInteger("4294967297");
        ArrayList<Threads> l1 = new ArrayList<Threads>();
        boolean repeat = true;

        Threads thread1 = new Threads(n);
        Threads thread2 = new Threads(n);
        Threads thread3 = new Threads(n);

        l1.add(thread1);
        l1.add(thread2);
        l1.add(thread3);

        thread1.start();
        thread2.start();
        thread3.start();

        while (repeat){
            for (Threads l : l1){
                if (l.getValue() == null){
                    continue;
                }
                else{
                    System.out.println("The factor is: " + l.getValue());
                    repeat = false;
                    break;
                }
                
            }
        }

    }

    //Precondition: n is a semi-prime number.
    //Postcondition: the returned value is a prime factor of n;
    public static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");//begin the guess from "2"
        BigInteger zero = new BigInteger("0");
        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }
            i = i.add(new BigInteger("1"));
        }

        assert (false); //if this is reached, an error occurs.
        return null;
    }
}

class Threads extends Thread {
    private BigInteger n, value;

    public Threads(BigInteger n){
        this.n = n;
    }

    public void run(){
        value = FactorThreadNoInterrupt.factor(n);
    }

    public BigInteger getValue(){
        return value;
    }
}
