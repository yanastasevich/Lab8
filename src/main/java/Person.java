import java.util.Random;

public class Person {
    private static final int p = 79;
    private static final int g = 10;

    private static int port = 9876;


    public static int[] generateRandomPrivateKeys() {
        Random random = new Random();
        return random.ints(2, 1, getP()).toArray();
    }

    public static double computePublicKey(int privateKey) {
        return Math.pow(getG(), privateKey) % getP();
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
