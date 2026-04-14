package pl.blockcraft.access;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.IOException;
import java.util.List;

public interface BlockFetcherInterface {
    EthBlock.Block getLatestBlock();
    List<EthBlock.Block> getLatestBlocks(int n) throws IOException;
}