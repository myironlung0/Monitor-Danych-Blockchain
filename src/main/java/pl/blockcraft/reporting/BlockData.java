package pl.blockcraft.reporting;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.logic.LogicInterface;

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

    public static BlockData fromBlock(EthBlock.Block block, LogicInterface logic) {
        return new BlockData(
                block.getNumber().longValue(),
                block.getHash(),
                logic.getNumberOfTransactions(block).intValue()
        );
    }
}