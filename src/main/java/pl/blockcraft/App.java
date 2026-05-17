package pl.blockcraft;

import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.access.*;
import pl.blockcraft.exceptions.BlockchainDataException;
import pl.blockcraft.exceptions.ConnectionException;
import pl.blockcraft.logic.LogicInterface;
import pl.blockcraft.logic.LogicUnit;
import pl.blockcraft.reporting.BlockData;
import pl.blockcraft.reporting.ConsoleReporter;
import pl.blockcraft.reporting.TransactionData;

import java.math.BigInteger;
import java.util.List;

/**
 * MAIN CLASS
 *
 */

public class App {
    private static final List<EthBlock.Block> allBlocks = new java.util.ArrayList<>();
    private static final List<EthBlock.TransactionObject> allTransactions = new java.util.ArrayList<>();
    private static final LogicInterface logic = new LogicUnit();
    private static final int UPDATE_TIME = 5000;

    public static void main(String[] args) {
        try {

            Web3j web3j = Web3jProvider.connectWebSocket();
            BlockFetcherInterface bFetcher = new BlockFetcher(web3j);
            TransactionFetcherInterface txsFetcher = new TransactionFetcher(web3j);
            String clientVersion = Web3jProvider.getClientVersion(web3j);
            System.out.println("Connection successful, client version: " + clientVersion);

            // BLOCKS
            List<EthBlock.Block> initialBlocks = bFetcher.getLatestBlocks(100);

            for (EthBlock.Block block : initialBlocks) {
                allBlocks.add(block);
                BlockData dto = BlockData.fromBlock(block, logic);
                ConsoleReporter.reportBlock(dto);
            }

            // TRANSACTIONS
            List<EthBlock.TransactionObject> txList = txsFetcher.getTransactionsFromLatestBlocks(10, bFetcher);

            for (EthBlock.TransactionObject tx : txList) {
                allTransactions.add(tx);
                TransactionData dto = TransactionData.fromTransaction(tx, logic);
                ConsoleReporter.reportTransaction(dto);
            }

            // MAIN LOOP - POLLING
//            System.out.println("\nUPDATING EVERY " + UPDATE_TIME/1000 + " SECONDS");
//            BigInteger lastBlock = bFetcher.getLatestBlock().getNumber();
//            while (true) {
//                BigInteger currentBlock = bFetcher.getLatestBlock().getNumber();
//
//                if (currentBlock.compareTo(lastBlock) > 0) {
//                    System.out.println("New blocks: " + lastBlock + " -> " + currentBlock); // for testing; delete later
//
//                    List<EthBlock.Block> newBlocks = bFetcher.getLatestBlocks(
//                            currentBlock.subtract(lastBlock).intValue()
//                    );
//                    for (EthBlock.Block block : newBlocks) {
//                        allBlocks.add(block);
//                        BlockData dto = BlockData.fromBlock(block, logic);
//                        ConsoleReporter.reportBlock(dto);
//                    }
//
//                    List<EthBlock.TransactionObject> newTxs = txsFetcher
//                            .getTransactionsFromLatestBlocks(
//                                    currentBlock.subtract(lastBlock).intValue(), bFetcher);
//                    for (EthBlock.TransactionObject tx : newTxs) {
//                        allTransactions.add(tx);
//                        TransactionData dto = TransactionData.fromTransaction(tx, logic);
//                        ConsoleReporter.reportTransaction(dto);
//                    }
//
//                    lastBlock = currentBlock;
//                }
//
//                Thread.sleep(UPDATE_TIME);
//            }

            System.out.println("\n-------BEGIN MONITORING INCOMING BLOCKS AND TRANSACTIONS-------\n");
            Disposable subscriptionB = web3j.blockFlowable(false).subscribe(
                    b -> {
                        //System.out.println("\nSUBSCRIPTION FROM BLOCK"); // usun pozniej
                        EthBlock.Block block = b.getBlock();
                        allBlocks.add(block);
                        BlockData dto = BlockData.fromBlock(block, logic);
                        ConsoleReporter.reportBlock(dto);
                    },
                    error -> System.err.println("Block error: " + error.getMessage())
            );

            Disposable subscriptionTx = web3j.transactionFlowable().subscribe(
                    tx -> {
                        //System.out.println("\nSUBSCRIPTION FROM TX"); // usun pozniej
                        allTransactions.add((EthBlock.TransactionObject) tx);
                        TransactionData dto = TransactionData.fromTransaction((EthBlock.TransactionObject) tx, logic);
                        ConsoleReporter.reportTransaction(dto);
                    },
                    error -> System.err.println("Transaction error: " + error.getMessage())
            );

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nClosing monitor...");
                ConsoleReporter.reportSummary(allBlocks, allTransactions, logic);

                subscriptionB.dispose();
                subscriptionTx.dispose();

                web3j.shutdown();
            }));

        } catch (BlockchainDataException e) {
            System.err.println("Blockchain data error: " + e.getMessage());
            System.exit(1);
        } catch (ConnectionException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.exit(1);
        }
    }
}