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

        Eve eve = new Eve();
        eve.startConnection("127.0.0.1", 6666);

        Integer aliceSecretKey = alice.computeSecretKey(bobPublicKey);
        System.out.println("Alice generated secret key " + aliceSecretKey);
        Integer bobSecretKey = bob.computeSecretKey(alicePublicKey);
        System.out.println("Bob generated secret key " + bobSecretKey);

        String aliceSecretMessage = alice.encryptData("Hi from Alice", aliceSecretKey);
        System.out.println("Alice sent secret message " + aliceSecretMessage);
        String bobSecretMessage = bob.encryptData("Hi from Bob", bobSecretKey);
        System.out.println("Bob sent secret message " + bobSecretMessage);

        String aliceDecryptionResult = alice.readSecretMessage(bobSecretMessage, aliceSecretKey);
        System.out.println("Alice thinks Bob said " + aliceDecryptionResult);
        String bobDecryptionResult = alice.readSecretMessage(aliceSecretMessage, bobSecretKey);
        System.out.println("Bob thinks Alice said " + bobDecryptionResult);

        assertEquals(aliceSecretKey, bobSecretKey);
        assertEquals("hifrombob", aliceDecryptionResult);
        assertEquals("hifromalice", bobDecryptionResult);

    }
}
