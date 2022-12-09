import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Alice extends Person {
    public static int x = generateRandomPrivateKeys()[0];
    public static double publicKeyAlice = computePublicKey(x);

    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        Socket clientSocket = new Socket(host.getHostName(), getPort());
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //establish socket connection to server
        String line = "";
        while (!line.equals("exit")) {
            line = in.readLine();

            out.write("public key Alice " + publicKeyAlice);
        }
        //close resources
        in.close();
        out.close();
        clientSocket.close();
    }
}
