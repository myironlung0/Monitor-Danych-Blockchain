package pl.blockcraft.reporting;

public class ConsoleReporter {
    public static void reportBlock(BlockData block) {
        System.out.println("\n------[BLOCK]------");
        System.out.println("Number: " + block.getBlockNumber());
        System.out.println("Hash: " + block.getBlockHash());
        System.out.println("Transactions: " + block.getTransactionCount());
    }

    public static void reportTransaction(TransactionData tx) {
        System.out.println("------[TRANSACTION]------");
        System.out.println("Hash: " + tx.getTransactionHash());
        System.out.println("Sender: " + tx.getSender());
        System.out.println("Receiver: " + tx.getReceiver());
        System.out.println("Value (Eth): " + tx.getEth());
        System.out.println("Gas: " + tx.getGas());
    }
}
