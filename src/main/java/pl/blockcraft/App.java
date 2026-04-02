package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import pl.blockcraft.access.Web3jProvider;

import java.io.IOException;

/**
 * MAIN CLASS
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Web3j web3j = Web3jProvider.connect();
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            System.out.println("Connection successful, client version: " + clientVersion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        web3j.shutdown();
    }
}
