package pl.blockcraft.reporting;

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
}