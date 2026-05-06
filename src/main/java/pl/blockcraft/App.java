package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.access.*;
import pl.blockcraft.exceptions.BlockchainDataException;
import pl.blockcraft.exceptions.ConnectionException;

import java.util.List;

/**
 * MAIN CLASS
 *
 */

public class App 
{
    public static void main( String[] args ) throws ConnectionException, BlockchainDataException {
        Web3j web3j = Web3jProvider.connect();
        BlockFetcherInterface bFetcher = new BlockFetcher(web3j);
        TransactionFetcherInterface txsFetcher = new TransactionFetcher(web3j);

        try {
            String clientVersion = Web3jProvider.getClientVersion(web3j);
            System.out.println("Connection successful, client version: " + clientVersion);

            // TESTY
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

        } catch (BlockchainDataException e) {
            System.err.println("Blockchain data error: " + e.getMessage());
            System.exit(1);
        } catch (ConnectionException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.exit(1);
        }

        web3j.shutdown();
    }
}
