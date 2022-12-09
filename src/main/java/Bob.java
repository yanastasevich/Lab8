import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob extends Person {
    public static int y = generateRandomPrivateKeys()[1];
    public static double publicKeyBob = computePublicKey(y);

    private static ServerSocket server;


    public static void main(String[] args) throws IOException {
        server = new ServerSocket(getPort());
        while (true) {
            System.out.println("Bob waiting for Alice to publish her public key");
            Socket socket = server.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageFromAlice = in.readLine();
            System.out.println("Message Received: " + messageFromAlice);
            out.write("Hi Alice " + publicKeyBob);
           // out.write("exit");

            in.close();
            out.close();
            socket.close();

            // todo: do a break of server communication with writing exit
            // what is below is incorrect
            if (messageFromAlice.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}
