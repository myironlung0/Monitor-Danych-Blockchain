package pl.blockcraft.access;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface TransactionFetcherInterface {
    List<EthBlock.TransactionObject> getTransactionList(BigInteger blockNum);
    List<EthBlock.TransactionObject> getTransactionsFromLatestBlocks(int n, BlockFetcherInterface blockFetcher)throws IOException;
    }
