package pl.blockcraft.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogicUnitTest
{
    EthBlock.Block block;
    ArrayList<EthBlock.Block> blockList;
    LogicUnit logicUnit;
    ArrayList<EthBlock.TransactionObject> transactionList;
    EthBlock.TransactionObject transaction;


    @BeforeEach
    void setUp()
    {

        transaction = new EthBlock.TransactionObject();
        transaction.setValue("0x22222");
        transaction.setFrom("0x11");
        transaction.setTo("0x22");

        EthBlock.TransactionObject transaction2 = new EthBlock.TransactionObject();
        transaction2.setValue("0x33333");

        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(new EthBlock.TransactionObject());
        transactionList.add(new EthBlock.TransactionObject());
        transactionList.add(new EthBlock.TransactionObject());

        ArrayList<EthBlock.TransactionObject> transactionList2 = new ArrayList<>();
        transactionList2.add(new EthBlock.TransactionObject());
        transactionList2.add(new EthBlock.TransactionObject());
        transactionList2.add(new EthBlock.TransactionObject());

        block = new EthBlock.Block();
        block.setTransactions(Collections.unmodifiableList(transactionList));
        block.setGasUsed("0x220000");

        EthBlock.Block block2 = new EthBlock.Block();
        block2.setTransactions(Collections.unmodifiableList(transactionList2));
        block2.setGasUsed("0x12345");

        blockList = new ArrayList<>();
        blockList.add(new EthBlock.Block());
        blockList.add(block2);
        blockList.add(block);

        logicUnit = new LogicUnit();
    }

    @Test
    void testGetNumberOfBlocks()
    {
        BigInteger numberOfBlocks = logicUnit.getNumberOfBlocks(blockList);

        assertEquals(3, numberOfBlocks.intValue());
    }

    @Test
    void testGetNumberOfTransactions_BigInteger()
    {
        BigInteger numberOfTransactions = logicUnit.getNumberOfTransactions(block);

        assertEquals(5, numberOfTransactions.intValue());
    }

    @Test
    void testGetNumberOfTransactions_List()
    {
        List<BigInteger> numberOfTransactions = logicUnit.getNumberOfTransactions(blockList);

        assertArrayEquals(new BigInteger[]
                {   BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.valueOf(5)},
                numberOfTransactions.toArray());
    }

    @Test
    void getTotalNumberOfTransactions()
    {
        BigInteger numberOfTransactions = logicUnit.getTotalNumberOfTransactions(blockList);

        assertEquals(8, numberOfTransactions.intValue());
    }

    @Test
    void testGetGas_BigInteger()
    {
        BigInteger gas = logicUnit.getGas(block);
        assertEquals(BigInteger.valueOf(0x220000), gas);
    }

    @Test
    void testGetGas_List()
    {
        List<BigInteger> gas = logicUnit.getGas(blockList);
        assertArrayEquals(new BigInteger[]
                {BigInteger.ZERO, BigInteger.valueOf(0x12345), BigInteger.valueOf(0x220000)},
                gas.toArray());
    }

    @Test
    void testGetTotalGas()
    {
        BigInteger gas = logicUnit.getTotalGas(blockList);
        assertEquals(BigInteger.valueOf(0x232345), gas);
    }

    @Test
    void testGetAverageGas()
    {
        BigInteger gas = logicUnit.getAverageGas(blockList);
        assertEquals(BigInteger.valueOf((0x220000+0x12345)/3), gas);
    }

    @Test
    void testGetValueOfTransaction()
    {
        BigInteger value = logicUnit.getValueOfTransaction(transaction);
        assertEquals(BigInteger.valueOf(0x22222), value);
    }

    @Test
    void testGetValueOfTransactions()
    {
        List<BigInteger> values = logicUnit.getValueOfTransactions(transactionList);
        assertArrayEquals(new BigInteger[]
        {
            BigInteger.valueOf(0x22222),
            BigInteger.valueOf(0x33333),
            BigInteger.ZERO,
            BigInteger.ZERO,
            BigInteger.ZERO
        }, values.toArray());
    }

    @Test
    void getTotalValueOfTransactions()
    {
        BigInteger totalValue = logicUnit.getTotalValueOfTransactions(transactionList);
        assertEquals(BigInteger.valueOf(0x55555), totalValue);
    }

    @Test
    void getSender()
    {
        String sender = logicUnit.getSender(transaction);
        assertEquals("0x11", sender);
        String sender2 = logicUnit.getSender(new EthBlock.TransactionObject());
        assertNull(sender2);
    }

    @Test
    void getReceiver()
    {
        String receiver = logicUnit.getReceiver(transaction);
        assertEquals("0x22", receiver);
        String receiver2 = logicUnit.getReceiver(new EthBlock.TransactionObject());
        assertNull(receiver2);
    }
}