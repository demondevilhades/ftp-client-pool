package awesome.ftp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import awesome.ftp.FTPClientFactory;

/**
 * 
 * @author awesome
 */
public class FTPClientFactoryTest {

    private String host = "";
    private int port = 21;
    private String username = "";
    private String password = "";
    private int dataTimeout = 10000;
    private String encoding = "utf-8";
    private String basePath = "/";

    /**
     * 
     * @throws IOException
     */
    @Test
    public void testConnect() throws IOException {
        FTPClient ftpClient = new FTPClient();
        FTPClientFactory.connect(ftpClient, host, port, username, password, dataTimeout, encoding, basePath);
        assertTrue(ftpClient.sendNoOp());
    }
}
