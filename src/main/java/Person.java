import java.util.Random;

public class Person {

    public final int p = 79;
    // find if ten is a fitting number for g
    public final int g = 10;

    public int[] generateRandomPrivateKeys() {
        Random random = new Random();
        return random.ints(2, 1, p).toArray();
    }

    public double computePublicKey(int privateKey){
        return Math.pow(g, privateKey) % p;
    }
}
