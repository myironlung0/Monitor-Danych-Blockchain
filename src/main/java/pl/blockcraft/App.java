package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import pl.blockcraft.access.BlockFetcher;
import pl.blockcraft.access.Web3jProvider;

import java.util.List;
import java.io.IOException;

/**
 * MAIN CLASS
 *
 */

public class App 
{
    // TODO: obsłuyć błędy
    public static void main( String[] args ) throws IOException {
        Web3j web3j = Web3jProvider.connect();
        BlockFetcher fetcher = new BlockFetcher(web3j);

        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            System.out.println("Connection successful, client version: " + clientVersion);

            EthBlock.Block block = fetcher.getLatestBlock();

            List<EthBlock.Block> blockList = fetcher.getLatestBlocks(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        web3j.shutdown();
    }
}
