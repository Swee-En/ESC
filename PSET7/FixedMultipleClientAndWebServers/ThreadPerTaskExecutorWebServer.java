import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;

/**
 * Question for Cohort Exercise 2.
 */
public class ThreadPerTaskExecutorWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = new ThreadPerTaskExecutor();
    // private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws Exception {
        // If the client process is interrupted abruptly and the socket is not closed properly, this
        // will require a restart of the server to properly re-establish an open server socket again
        // available for connections (a "SocketException: Connection reset" error might be raised on
        // Windows and Mac).
        try (ServerSocket socket = new ServerSocket(54321, 100000000)) {
            while (true) {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        try {
                            handleRequest(connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                exec.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an issue with the socket connection.");
        }
    }

    private static void handleRequest(Socket connection) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BigInteger number = new BigInteger(in.readLine());
        BigInteger result = factor(number);
        // System.out.println("sending results: " + String.valueOf(result));
        out.println(result);
        out.flush();
        in.close();
        out.close();
        connection.close();
    }

    private static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert (false);
        return null;
    }
}


// Thread per task
class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}
