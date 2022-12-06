import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob extends Person {
    public int y = generateRandomPrivateKeys()[1];
    public double publicKeyBob = computePublicKey(y);

    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            System.out.println("Bob waiting for Alice to publish her public key");
            Socket socket = server.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageFromAlice = in.readLine();
            System.out.println("Message Received: " + messageFromAlice);
            out.write("Hi Alice ");

            in.close();
            out.close();
            socket.close();

            if (messageFromAlice.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}
