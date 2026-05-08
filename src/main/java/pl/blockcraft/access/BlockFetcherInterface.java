package pl.blockcraft.access;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.exceptions.BlockchainDataException;

import java.util.List;

public interface BlockFetcherInterface {
    EthBlock.Block getLatestBlock() throws BlockchainDataException;
    List<EthBlock.Block> getLatestBlocks(int n) throws BlockchainDataException;;
}