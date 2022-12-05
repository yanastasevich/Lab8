import java.util.Random;

public class Person {

    public final int p = 79;
    public final int g = 10;

    public int[] generateRandomPrivateKeys() {
        Random random = new Random();
        return random.ints(2, 1, p).toArray();
    }

    public double computePublicKey(int privateKey){
        return Math.pow(g, privateKey) % p;
    }
}
