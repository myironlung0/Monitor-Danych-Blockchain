package pl.blockcraft.access;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.exceptions.BlockchainDataException;

import java.math.BigInteger;
import java.util.List;

public interface TransactionFetcherInterface {
    List<EthBlock.TransactionObject> getTransactionList(BigInteger blockNum) throws BlockchainDataException;
    List<EthBlock.TransactionObject> getTransactionsFromLatestBlocks(int n, BlockFetcherInterface blockFetcher)throws BlockchainDataException;
    }
