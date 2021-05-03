import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
 
class ConcurrentHashMapDemo {
 
    public static void main(String[] args)
    {
        ReentrantLock lock = new ReentrantLock();
        System.out.println("Locked: " + lock.isLocked());
        // create an instance of
        // ConcurrentHashMap
        ConcurrentHashMap<Integer, String> m
            = new ConcurrentHashMap<>();
 
        // Insert mappings using
        // put method
        m.put(100, "Hello");
        m.put(100, "Hello");
        m.put(100, "Hello");
        m.put(101, "Geeks");
        m.put(102, "Geeks");
        m.put(103, "Hello");
        System.out.println(m);
    }
}