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
    private static String clientName;

    protected Person(String name) {
        this.clientName = name;
        System.out.println(this.clientName + " was born.");
        privateKey = generateRandomPrivateKey();
    }

    protected void startConnection(String ip, int port) throws IOException {
        System.out.println(this.clientName + " starts connecting to the server.");
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    protected void stopConnection() throws IOException {
        System.out.println(this.clientName + " died.");
        in.close();
        out.close();
        clientSocket.close();
    }

    protected String sendPublicKey() throws IOException {
        int publicKey = computePublicKey();
        out.println(publicKey);
        String resp = in.readLine();
        return resp;
    }


    protected static int computePublicKey() {
        return (int) Math.pow(getG(), privateKey) % getP();
    }

    protected Integer computeSecretKey (String publicKeyString) {
        Integer publicKey = Integer.valueOf(publicKeyString);
        return (int) Math.pow(publicKey, privateKey) % p;
    }

    protected int generateRandomPrivateKey() {
        Random random = new Random();
        Integer privateKey = random.nextInt(getP())+1;
        System.out.println(this.clientName + " picked private key " + privateKey);
        return privateKey;
    }

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static String encryptData(String inputStr, int shiftKey)
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

    private static String decryptData(String inputStr, int shiftKey)
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

    private static int getG() {
        return g;
    }

    private static int getP() {
        return p;
    }

    private static int getPort() {
        return port;
    }
}
