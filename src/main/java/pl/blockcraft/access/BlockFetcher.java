package pl.blockcraft.access;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BlockFetcher implements BlockFetcherInterface{
    private final Web3j web3j;

    public BlockFetcher(Web3j web3j){
        this.web3j = web3j;
    }

    // TODO: obsłuyć błędy
    public EthBlock.Block getLatestBlock() {

        EthBlock.Block latestBlock = null;
        try {
            latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();

        }catch(IOException e) {
            e.printStackTrace();
        }
        return latestBlock;
    }

    // returns n most recent blocks
    public List<EthBlock.Block> getLatestBlocks(int n) throws IOException{
        List<EthBlock.Block> blockList = new ArrayList<>();
        BigInteger latestBlockNum = getLatestBlock().getNumber();

        for(int i = 0; i < n; i++){
            BigInteger blockNum = latestBlockNum.subtract(BigInteger.valueOf(i));
            EthBlock.Block block = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNum), false).send().getBlock();

            blockList.add(block);
            //System.out.println("Blok: " + block.getNumber());
        }

        return blockList;
    }




}
