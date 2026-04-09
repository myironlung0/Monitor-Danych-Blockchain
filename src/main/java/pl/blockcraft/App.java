package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import pl.blockcraft.access.*;

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
        BlockFetcherInterface bFetcher = new BlockFetcher(web3j);
        TransactionFetcherInterface txsFetcher = new TransactionFetcher(web3j);

        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            System.out.println("Connection successful, client version: " + clientVersion);

            EthBlock.Block block = bFetcher.getLatestBlock();

            List<EthBlock.Block> blockList = bFetcher.getLatestBlocks(10);

            //List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionList(fetcher.getLatestBlock().getNumber());

//            for(EthBlock.TransactionObject transaction : txList){
//                System.out.println("Transaction index: " + transaction.getTransactionIndex());
//                System.out.println("Transaction hash: " + transaction.getHash());
//                System.out.println("Transaction gas: " + transaction.getGas());
//
//            }

            List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionsFromLatestBlocks(10, bFetcher);
            System.out.println("Lacznie transakcji: " + txList.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        web3j.shutdown();
    }
}
