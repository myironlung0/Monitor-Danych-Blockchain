package pl.blockcraft.access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import pl.blockcraft.exceptions.BlockchainDataException;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionFetcherTest {
    private Web3j web3jMock;
    private TransactionFetcher fetcher;

    @BeforeEach
    void setUp() {
        web3jMock = mock(Web3j.class);
        fetcher = new TransactionFetcher(web3jMock);
    }

    @Test
    void constructor_createsInstance() {
        assertNotNull(fetcher);
    }

    @Test
    void getTransactionList_throwsBlockchainDataException_onIOException() throws IOException{
        Request requestMock = mock(Request.class);
        when(web3jMock.ethGetBlockByNumber(any(), anyBoolean())).thenReturn(requestMock);
        when(requestMock.send()).thenThrow(new IOException("network error"));

        assertThrows(BlockchainDataException.class, () -> fetcher.getTransactionList(BigInteger.ONE));
    }

}
