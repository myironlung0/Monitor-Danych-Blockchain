package pl.blockcraft.access;

/*
ACCESS LAYER
Web3j connection configuration
 */


import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Web3jProvider {

    private static final String CONFIG_FILE = "config.properties";

    // TODO: obsłuyć błędy

    public static Web3j connect() throws IOException {
        return Web3j.build(new HttpService(getNodeUrl()));
    }

    // TODO: obsłuyć błędy
    public static String getNodeUrl() throws IOException {
        Properties props = new Properties();


        try(InputStream input = Web3jProvider.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("FILE NOT FOUND " + CONFIG_FILE);
            }
            props.load(input);
            input.close();

            String url = props.getProperty("ethereum.node.url");
            if (url == null) {
                throw new IllegalStateException("NO KEY 'ethereum.node.url' IN " + CONFIG_FILE);
            }
            return url;
        }
    }
}
