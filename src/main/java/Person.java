import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Person {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static final int p = 79;
    private static final int g = 10;
    private static int port = 6666;
    private static int privateKey;

    public static String clientName;

    public Person(String name) {
        this.clientName = name;
        privateKey = generateRandomPrivateKeys()[0];
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendPublicKey() throws IOException {
        int publicKey = computePublicKey(privateKey);
        out.println(publicKey);
        String resp = in.readLine();
        return resp;
    }

    public String receivePublicKey(String publicKey) throws IOException {
        String secretKey = String.valueOf(computeSecretKey(Double.parseDouble(publicKey)));
        return secretKey;
    }

    public Integer computeSecretKey (double publicKey) {
        return (int) Math.pow(publicKey, privateKey) % p;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    public static int[] generateRandomPrivateKeys() {
        Random random = new Random();
        return random.ints(2, 1, getP()).toArray();
    }

    public static int computePublicKey(int privateKey) {
        return (int) Math.pow(getG(), privateKey) % getP();
    }

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String encryptData(String inputStr, int shiftKey)
    {
        inputStr = inputStr.toLowerCase();
        String encryptStr = "";
        for (int i = 0; i < inputStr.length(); i++)
        {
            int pos = ALPHABET.indexOf(inputStr.charAt(i));
            int encryptPos = (shiftKey + pos) % 26;
            char encryptChar = ALPHABET.charAt(encryptPos);
            encryptStr += encryptChar;
        }
        return encryptStr;
    }

    public static String decryptData(String inputStr, int shiftKey)
    {
        inputStr = inputStr.toLowerCase();
        String decryptStr = "";
        for (int i = 0; i < inputStr.length(); i++)
        {
            int pos = ALPHABET.indexOf(inputStr.charAt(i));
            int decryptPos = (pos - shiftKey) % 26;
            if (decryptPos < 0){
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);
            decryptStr += decryptChar;
        }
        return decryptStr;
    }

    public static int getG() {
        return g;
    }

    public static int getP() {
        return p;
    }

    public static int getPort() {
        return port;
    }
}
