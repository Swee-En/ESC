import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

// For production purposes, task-per-thread has some drawbacks. Thread creation and teardown
// involves the JVM and OS. Active threads consume extra memory to provide their respective stacks.
// If there are less CPUs than threads, idle threads also consume memory. There is also a stability
// limit on the number of concurrent threads.
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws Exception {
        // If the client process is interrupted abruptly and the socket is not closed properly, this
        // will require a restart of the server to properly re-establish an open server socket again
        // available for connections (a "SocketException: Connection reset" error might be raised on
        // Windows and Mac).
        try (ServerSocket socket = new ServerSocket(54321, 100000000)) {
            while (true) {
                final Socket connection = socket.accept();
                new WorkerThread(connection).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an issue with the socket connection.");
        }
    }
}


class WorkerThread extends Thread {
    private final Socket connection;

    public WorkerThread(Socket connection) {
        this.connection = connection;
    }

    public void run() {
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
            e.printStackTrace();
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
