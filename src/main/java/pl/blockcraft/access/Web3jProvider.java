package pl.blockcraft.access;

/* ACCESS LAYER - Web3j connection configuration */

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import pl.blockcraft.exceptions.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Properties;

public class Web3jProvider {

    private static final String CONFIG_FILE = "config.properties";

    public static Web3j connect() throws ConnectionException {
        try {
            return Web3j.build(new HttpService(getNodeUrl("ethereum.node.url")));
        } catch (ConnectionException e) {
            throw new ConnectionException("Failed to connect to Ethereum network", e);
        }
    }

    public static String getNodeUrl(String propertyName) throws ConnectionException {
        Properties props = new Properties();

        try(InputStream input = Web3jProvider.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("FILE NOT FOUND " + CONFIG_FILE);
            }
            props.load(input);

            String url = props.getProperty(propertyName);
            if (url == null) {
                throw new ConnectionException("NO KEY 'ethereum.node.url' IN " + CONFIG_FILE, null);
            }
            return url;
        }catch (IOException e) {
            throw new ConnectionException("Failed to read config file", e);
        }
    }

    public static String getClientVersion(Web3j web3j) throws ConnectionException {
        try {
            return web3j.web3ClientVersion().send().getWeb3ClientVersion();
        } catch (IOException e) {
            throw new ConnectionException("Failed to get client version", e);
        }
    }

    public static Web3j connectWebSocket() throws ConnectionException{
        try{
            WebSocketService wsService = new WebSocketService(getNodeUrl("ethereum.node.wss"), true);
            wsService.connect();
            return Web3j.build(wsService);
        }catch(ConnectException ce){
            throw new ConnectionException("Failed to connect via WebSocket", ce);
        }
    }
}
