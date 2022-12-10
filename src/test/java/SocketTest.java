import org.testng.annotations.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocketTest {
    @Test
public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        System.out.println("Test has started...");
        Person alice = new Person("Alice");
        alice.startConnection("127.0.0.1", 6666);
        String alicePublicKey = alice.sendPublicKey();
        System.out.println("Alice generated and sent public key " + alicePublicKey);
        // alice.stopConnection(); // TODO: what does this do?

        Person bob = new Person("Bob");
        bob.startConnection("127.0.0.1", 6666);
        String bobPublicKey = bob.sendPublicKey();
        System.out.println("Bob generated and sent public key " + bobPublicKey);

        Integer aliceSecretKey = alice.computeSecretKey(bobPublicKey);
        System.out.println("Alice generated secret key " + aliceSecretKey);
        Integer bobSecretKey = bob.computeSecretKey(alicePublicKey);
        System.out.println("Bob generated secret key " + bobSecretKey);

        assertEquals(aliceSecretKey, bobSecretKey);
    }
}
