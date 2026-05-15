package pl.blockcraft.reporting;

import org.web3j.protocol.core.methods.response.EthBlock;
import pl.blockcraft.logic.LogicInterface;

public class TransactionData {

    private final String transactionHash;
    private final String sender;
    private final String receiver;
    private final double eth;
    private final long gas;

    public TransactionData(String transactionHash, String sender, String receiver, double eth, long gas) {
        this.transactionHash = transactionHash;
        this.sender = sender;
        this.receiver = receiver;
        this.eth = eth;
        this.gas = gas;
    }

    public String getTransactionHash() {return transactionHash;}
    public String getSender() {return sender;}
    public String getReceiver() {return receiver;}
    public double getEth() {return eth;}
    public long getGas() {return gas;}

    public static TransactionData fromTransaction(EthBlock.TransactionObject tx, LogicInterface logic) {
        return new TransactionData(
                tx.getHash(),
                logic.getSender(tx),
                logic.getReceiver(tx),
                logic.getValueOfTransaction(tx).doubleValue(),
                tx.getGas().longValue()
        );
    }
}