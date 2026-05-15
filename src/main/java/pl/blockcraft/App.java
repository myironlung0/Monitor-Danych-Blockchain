package pl.blockcraft;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.access.*;
import pl.blockcraft.exceptions.BlockchainDataException;
import pl.blockcraft.exceptions.ConnectionException;
import pl.blockcraft.reporting.BlockData;
import pl.blockcraft.reporting.ConsoleReporter;
import pl.blockcraft.reporting.TransactionData;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * MAIN CLASS
 *
 */

public class App {
    private static final List<BlockData> allBlocks = new ArrayList<>();
    private static final List<TransactionData> allTransactions = new ArrayList<>();

    public static void main(String[] args) {
        try {

            Web3j web3j = Web3jProvider.connect();
            BlockFetcherInterface bFetcher = new BlockFetcher(web3j);
            TransactionFetcherInterface txsFetcher = new TransactionFetcher(web3j);
            String clientVersion = Web3jProvider.getClientVersion(web3j);
            System.out.println("Connection successful, client version: " + clientVersion);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nClosing monitor...");
                ConsoleReporter.reportSummary(allTransactions, allBlocks);

                web3j.shutdown();
            }));

            // BLOCKS
            List<EthBlock.Block> initialBlocks = bFetcher.getLatestBlocks(100);

            for (EthBlock.Block block : initialBlocks) {
                BlockData dto = BlockData.fromBlock(block);
                allBlocks.add(dto);
                ConsoleReporter.reportBlock(dto);
            }

            // TRANSACTIONS
            List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionsFromLatestBlocks(10, bFetcher);

            for (EthBlock.TransactionObject tx : txList) {
                TransactionData dto = TransactionData.fromTransaction(tx);
                allTransactions.add(dto);
                ConsoleReporter.reportTransaction(dto);
            }

            // MAIN LOOP
            BigInteger lastBlock = bFetcher.getLatestBlock().getNumber();
            while (true) {
                BigInteger currentBlock = bFetcher.getLatestBlock().getNumber();

                if (currentBlock.compareTo(lastBlock) > 0) {
                    System.out.println("new blocks: " + lastBlock + " -> " + currentBlock); // for testing; delete later

                    List<EthBlock.Block> newBlocks = bFetcher.getLatestBlocks(
                            currentBlock.subtract(lastBlock).intValue()
                    );
                    // reportowanie dodac
                    for (EthBlock.Block block : newBlocks) {
                        System.out.println("Blok #" + block.getNumber());
                    }
                    lastBlock = currentBlock;
                }

                Thread.sleep(5000); // sprawdzaj co 5 sekund
            }

        } catch (BlockchainDataException e) {
            System.err.println("Blockchain data error: " + e.getMessage());
            System.exit(1);
        } catch (ConnectionException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
