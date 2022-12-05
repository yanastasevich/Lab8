public class Alice extends Person{
    public int x = generateRandomPrivateKeys()[0];
    public double publicKeyAlice = computePublicKey(x);
}
