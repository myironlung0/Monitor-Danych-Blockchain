package pl.blockcraft.access;

/*
ACCESS LAYER
Web3j connection configuration
 */


import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3jProvider {

    // TO DO: przerzucic url do config file
    private static final String NODE_URL = "https://eth-sepolia.g.alchemy.com/v2/JkVjv2trp9Nb1-2PGqps5";

    public static Web3j connect() {
        return Web3j.build(new HttpService(NODE_URL));
    }
}
