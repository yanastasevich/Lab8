import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Random;

public class Eve {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    protected Eve() {
        System.out.println("A wild Eve appeared.");
    }

    protected void startConnection(String ip, int port) throws IOException {
        System.out.println("Eve starts connecting to the server.");
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    protected void stopConnection() throws IOException {
        System.out.println("Eve died");
        in.close();
        out.close();
        clientSocket.close();
    }

}
