import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Alice extends Person{
    public int x = generateRandomPrivateKeys()[0];
    public double publicKeyAlice = computePublicKey(x);


}
