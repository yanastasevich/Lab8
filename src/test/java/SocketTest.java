import org.testng.annotations.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SocketTest {
    @Test
public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        System.out.println("Test has started...");
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        alice.startConnection("127.0.0.1", 6666);
        bob.startConnection("127.0.0.1", 6666);
        // alice.stopConnection(); // TODO: what does this do?

        String alicePublicKey = alice.sendPublicKey();
        System.out.println("Alice sends public key " + alicePublicKey);
        String bobPublicKey = bob.sendPublicKey();
        System.out.println("Bob send public key " + bobPublicKey);

        String aliceSecretKey = alice.receivePublicKey(bobPublicKey);
        System.out.println("Alice generated secret key " + alicePublicKey);
        String bobSecretKey = alice.receivePublicKey(alicePublicKey);
        System.out.println("Bob generated secret key " + alicePublicKey);

        assertEquals(aliceSecretKey, bobSecretKey);
    }
}
