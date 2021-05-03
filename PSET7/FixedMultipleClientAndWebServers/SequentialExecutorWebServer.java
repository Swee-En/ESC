import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;

public class SequentialExecutorWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = new WithinThreadExecutor();

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
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an issue with the socket connection.");
        }
    }

    private static void handleRequest(Socket connection) {
        try {
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            BigInteger number = new BigInteger(in.readLine());
            BigInteger result = factor(number);
            // System.out.println("sending results: " + String.valueOf(result));
            out.println(result);
            out.flush();
            in.close();
            out.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("error");
        }
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


// Sequential
class WithinThreadExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
