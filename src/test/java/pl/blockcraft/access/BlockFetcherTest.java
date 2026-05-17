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

public class BlockFetcherTest {
    private Web3j web3jMock;
    private BlockFetcher fetcher;

    @BeforeEach
    void setUp() {
        web3jMock = mock(Web3j.class);
        fetcher = new BlockFetcher(web3jMock);
    }

    @Test
    void constructor_storesWeb3j() {
        Web3j web3jMock = mock(Web3j.class);
        BlockFetcher fetcher = new BlockFetcher(web3jMock);
        assertNotNull(fetcher);
    }

    @Test
    void getLatestBlock_throwsBlockchainDataException_onIOException() throws IOException {
        Request requestMock = mock(Request.class);
        when(web3jMock.ethGetBlockByNumber(any(), anyBoolean())).thenReturn(requestMock);
        when(requestMock.send()).thenThrow(new IOException("network error"));

        // ethBlockNumber tez trzeba zamockowac
        Request blockNumRequest = mock(Request.class);
        EthBlockNumber ethBlockNumber = mock(EthBlockNumber.class);
        when(web3jMock.ethBlockNumber()).thenReturn(blockNumRequest);
        when(blockNumRequest.send()).thenReturn(ethBlockNumber);
        when(ethBlockNumber.getBlockNumber()).thenReturn(BigInteger.ONE);

        assertThrows(BlockchainDataException.class, () -> fetcher.getLatestBlock());
    }
}
