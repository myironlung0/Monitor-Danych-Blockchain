package pl.blockcraft.access;

import org.junit.jupiter.api.Test;
import pl.blockcraft.exceptions.ConnectionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Web3jProviderTest {

    @Test
    void getNodeUrl_throwsConnectionException_whenKeyMissing() {
        assertThrows(ConnectionException.class, () -> Web3jProvider.getNodeUrl("nonexistent.key"));
    }

    @Test
    void getNodeUrl_returnsUrl_whenKeyExists() throws ConnectionException {
        String url = Web3jProvider.getNodeUrl("ethereum.node.url");
        assertNotNull(url);
    }

    @Test
    void connect_returnsWeb3jInstance() throws ConnectionException {
        assertNotNull(Web3jProvider.connect());
    }

    @Test
    void connectWebSocket_returnsWeb3jInstance() throws ConnectionException {
        assertNotNull(Web3jProvider.connectWebSocket());
    }

}
