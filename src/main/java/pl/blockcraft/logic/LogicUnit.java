package pl.blockcraft.logic;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class LogicUnit implements LogicInterface
{

    @Override
    public BigInteger getNumberOfBlocks(List<EthBlock.Block> blocks)
    {
        return BigInteger.valueOf(blocks.size());
    }

    @Override
    public BigInteger getNumberOfTransactions(EthBlock.Block block)
    {
        return BigInteger.valueOf(block.getTransactions().size());
    }

    @Override
    public List<BigInteger> getNumberOfTransactions(List<EthBlock.Block> blocks)
    {
        List<BigInteger> result = new ArrayList<>();
        for(EthBlock.Block block : blocks){
            result.add(getNumberOfTransactions(block));
        }
        return result;
    }

    @Override
    public BigInteger getTotalNumberOfTransactions(List<EthBlock.Block> blocks)
    {
        BigInteger result = BigInteger.ZERO;
        for(EthBlock.Block block : blocks){
            result = result.add(getNumberOfTransactions(block));
        }
        return result;
    }

    @Override
    public BigInteger getGas(EthBlock.Block block)
    {
        return block.getGasUsed();
    }

    @Override
    public List<BigInteger> getGas(List<EthBlock.Block> blocks)
    {
        List<BigInteger> result = new ArrayList<>();
        for(EthBlock.Block block : blocks){
            result.add(getGas(block));
        }
        return result;
    }

    @Override
    public BigInteger getTotalGas(List<EthBlock.Block> blocks)
    {
        BigInteger result = BigInteger.ZERO;
        for(EthBlock.Block block : blocks){
            result = result.add(getGas(block));
        }
        return result;
    }

    @Override
    public BigInteger getAverageGas(List<EthBlock.Block> blocks)
    {
        int number = 0;
        BigInteger result = BigInteger.ZERO;
        for(EthBlock.Block block : blocks){
            result = result.add(getGas(block));
            number++;
        }
        return (number!=0 ? result.divide(BigInteger.valueOf(number)):BigInteger.ZERO);
    }

    @Override
    public BigInteger getValueOfTransaction(EthBlock.TransactionObject transaction)
    {
        return transaction.getValue();
    }

    @Override
    public List<BigInteger> getValueOfTransactions(List<EthBlock.TransactionObject> transactions)
    {
        List<BigInteger> result = new ArrayList<>();
        for(EthBlock.TransactionObject transaction : transactions){
            result.add(getValueOfTransaction(transaction));
        }
        return result;
    }

    @Override
    public BigInteger getTotalValueOfTransactions(List<EthBlock.TransactionObject> transactions)
    {
        BigInteger result = BigInteger.ZERO;
        for(EthBlock.TransactionObject transaction : transactions){
            result = result.add(getValueOfTransaction(transaction));
        }
        return result;
    }


    @Override
    public String getSender(EthBlock.TransactionObject transaction)
    {
        return transaction.getFrom();
    }

    @Override
    public String getReceiver(EthBlock.TransactionObject transaction)
    {
        return transaction.getTo();
    }
}
