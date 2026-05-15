package pl.blockcraft.reporting;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.logic.LogicInterface;

import java.math.BigInteger;
import java.util.List;

public class ConsoleReporter {

    public static void reportBlock(BlockData block) {
        System.out.println("\n------[BLOCK]------");
        System.out.println("Number: " + block.getBlockNumber());
        System.out.println("Hash: " + block.getBlockHash());
        System.out.println("Transactions: " + block.getTransactionCount());
    }

    public static void reportTransaction(TransactionData tx) {
        System.out.println("\n------[TRANSACTION]------");
        System.out.println("Hash: " + tx.getTransactionHash());
        System.out.println("Sender: " + tx.getSender());
        System.out.println("Receiver: " + tx.getReceiver());
        System.out.println("Value (ETH): " + tx.getEth());
        System.out.println("Gas: " + tx.getGas());
    }

    public static void reportSummary(List<EthBlock.Block> blocks, List<EthBlock.TransactionObject> transactions, LogicInterface logic) {
        System.out.println("\n------[SUMMARY]------");
        System.out.println("Total blocks: " + logic.getNumberOfBlocks(blocks));
        System.out.println("Total transactions: " + logic.getTotalNumberOfTransactions(blocks));
        System.out.println("Total gas: " + logic.getTotalGas(blocks));
        System.out.println("Average gas: " + logic.getAverageGas(blocks));
        System.out.println("Total ETH transferred: " + logic.getTotalValueOfTransactions(transactions));
    }
}