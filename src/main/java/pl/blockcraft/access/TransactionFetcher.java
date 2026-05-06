package pl.blockcraft.access;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.exceptions.BlockchainDataException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TransactionFetcher implements TransactionFetcherInterface{
    private final Web3j web3j;

    public TransactionFetcher(Web3j web3j) {
        this.web3j = web3j;
    }

    public List<EthBlock.TransactionObject> getTransactionList(BigInteger blockNum) throws BlockchainDataException{
        List<EthBlock.TransactionObject> transactionList = new ArrayList<>();
        try {
            List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(
                    new DefaultBlockParameterNumber(blockNum), true).send().getBlock().getTransactions();

            for(EthBlock.TransactionResult result: txs) {
                transactionList.add((EthBlock.TransactionObject)result.get());
            }
        }catch(IOException e) {
            throw new BlockchainDataException("Failed to fetch transactions for block" + blockNum, e);
        }
        return transactionList;
    }

    // returns transactions from n most recent blocks
    public List<EthBlock.TransactionObject> getTransactionsFromLatestBlocks(int n, BlockFetcherInterface blockFetcher)throws BlockchainDataException {
        List<EthBlock.TransactionObject> allTransactions = new ArrayList<>();
        try {
            BigInteger latestBlockNum = blockFetcher.getLatestBlock().getNumber();

            for (int i = 0; i < n; i++) {
                BigInteger blockNum = latestBlockNum.subtract(BigInteger.valueOf(i));
                List<EthBlock.TransactionObject> txs = getTransactionList(blockNum);
                allTransactions.addAll(txs);
                //System.out.println("Pobrano " + txs.size() + " transakcji z bloku #" + blockNum);
            }
        } catch (BlockchainDataException e) {
            throw new BlockchainDataException("Failed to fetch transactions from latest blocks", e);
        }

        return allTransactions;
    }
}
