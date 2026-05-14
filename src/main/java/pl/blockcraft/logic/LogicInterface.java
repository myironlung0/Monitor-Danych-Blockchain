package pl.blockcraft.logic;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.access.BlockFetcherInterface;

import java.math.BigInteger;
import java.util.List;

public interface LogicInterface
{
    BigInteger getNumberOfBlocks(List<EthBlock.Block> blocks);

    BigInteger getNumberOfTransactions(EthBlock.Block block);

    List<BigInteger> getNumberOfTransactions(List<EthBlock.Block> blocks);

    BigInteger getTotalNumberOfTransactions(List<EthBlock.Block> blocks);

    BigInteger getGas(EthBlock.Block block);

    List<BigInteger> getGas(List<EthBlock.Block> blocks);

    BigInteger getTotalGas(List<EthBlock.Block> blocks);

    BigInteger getAverageGas(List<EthBlock.Block> blocks);

    BigInteger getValueOfTransaction(EthBlock.TransactionObject transaction);

    List<BigInteger> getValueOfTransactions(EthBlock.TransactionObject transactions);

    BigInteger getTotalValueOfTransactions(List<EthBlock.TransactionObject> transactions);

    BigInteger getTotalValueOfTransactions(EthBlock.Block block);

    BigInteger getTotalValueOfTransactionsInBlocks(List<EthBlock.Block> blocks);

    String getSender(EthBlock.TransactionObject transaction);

    String getReceiver(EthBlock.TransactionObject transaction);
}
