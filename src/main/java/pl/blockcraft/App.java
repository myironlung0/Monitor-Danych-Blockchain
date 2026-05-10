package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.access.*;
import pl.blockcraft.exceptions.BlockchainDataException;
import pl.blockcraft.exceptions.ConnectionException;
import pl.blockcraft.reporting.BlockData;
import pl.blockcraft.reporting.ConsoleReporter;
import pl.blockcraft.reporting.TransactionData;

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

            // BLOCKS
            List<EthBlock.Block> blockList = bFetcher.getLatestBlocks(10);

            for (EthBlock.Block block : blockList) {
                BlockData dto = new BlockData(
                        block.getNumber().longValue(),
                        block.getHash(),
                        block.getTransactions().size()
                );
                ConsoleReporter.reportBlock(dto);
            }

            // TRANSACTIONS
            List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionsFromLatestBlocks(10, bFetcher);

            for (EthBlock.TransactionObject tx : txList) {
                TransactionData dto = new TransactionData(
                        tx.getHash(),
                        tx.getFrom(),
                        tx.getTo(),
                        tx.getValue().doubleValue(),
                        tx.getGas().longValue()
                );
                ConsoleReporter.reportTransaction(dto);
            }

            // TESTY
            EthBlock.Block block = bFetcher.getLatestBlock();

            //List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionList(fetcher.getLatestBlock().getNumber());

//            for(EthBlock.TransactionObject transaction : txList){
//                System.out.println("Transaction index: " + transaction.getTransactionIndex());
//                System.out.println("Transaction hash: " + transaction.getHash());
//                System.out.println("Transaction gas: " + transaction.getGas());
//
//            }

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
