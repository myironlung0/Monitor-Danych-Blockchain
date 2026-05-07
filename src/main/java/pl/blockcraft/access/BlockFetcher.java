package pl.blockcraft.access;

import org.web3j.abi.datatypes.Int;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.exceptions.BlockchainDataException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BlockFetcher implements BlockFetcherInterface{
    private final Web3j web3j;
    private static final int MAX_RETRIES = 3;

    public BlockFetcher(Web3j web3j){
        this.web3j = web3j;
    }

    // exponential backoff, simple rate limiting
    private EthBlock.Block getBlockWithRetry(BigInteger blockNum) throws BlockchainDataException{
        for(int i = 1; i <= MAX_RETRIES; i++){
            try{
                Thread.sleep(100);
                return web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNum), false).send().getBlock();
            }catch(InterruptedException ie){
                throw new BlockchainDataException("Failed to fetch block. Interrupted during rate limiting", ie);
            }catch(IOException e){
                if(i == MAX_RETRIES){
                    throw new BlockchainDataException("Failed to fetch block nr. " + blockNum, e);
                }

                try{
                    Thread.sleep((long) Math.pow(2, i) * 1000L);
                }catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // restore the flag
                    throw new BlockchainDataException("Interrupted while retrying block fetch", ie);
                }

                System.err.println("Attempt " + i + " failed, retrying...");
            }
        }
        throw new BlockchainDataException("Failed to fetch block nr. " + blockNum + " after " + MAX_RETRIES + " attempts", null);
    }

    public EthBlock.Block getLatestBlock() throws BlockchainDataException{
        try{
            return getBlockWithRetry(web3j.ethBlockNumber().send().getBlockNumber());
        }catch (IOException e) {
            throw new BlockchainDataException("Failed to fetch latest block", e);
        }
    }

    // returns n most recent blocks
    public List<EthBlock.Block> getLatestBlocks(int n) throws BlockchainDataException {
        List<EthBlock.Block> blockList = new ArrayList<>();
        BigInteger latestBlockNum = getLatestBlock().getNumber();

        for(int i = 0; i < n; i++){
            BigInteger blockNum = latestBlockNum.subtract(BigInteger.valueOf(i));
            blockList.add(getBlockWithRetry(blockNum));
        }

        return blockList;
    }
}
