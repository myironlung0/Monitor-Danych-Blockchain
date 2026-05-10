package pl.blockcraft.reporting;

public class BlockData {
    private final long blockNumber;
    private final String blockHash;
    private final int transactionCount;

    public BlockData(long blockNumber, String blockHash, int transactionCount) {
        this.blockNumber = blockNumber;
        this.blockHash = blockHash;
        this.transactionCount = transactionCount;
    }

    public long getBlockNumber() {return blockNumber;}

    public String getBlockHash() {return blockHash;}

    public int getTransactionCount() {return transactionCount;}
}