package pl.blockcraft.reporting;

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
        System.out.println("Value (Eth): " + tx.getEth());
        System.out.println("Gas: " + tx.getGas());
    }

    public static void reportSummary(List<TransactionData> transactions, List<BlockData> blocks) {
        System.out.println("\n------[SUMMARY]------");
        System.out.println("Total blocks: " + blocks.size());
        System.out.println("Total transactions: " + transactions.size());
    }
}
