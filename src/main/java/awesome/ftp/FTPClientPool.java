package awesome.ftp;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 
 * @author awesome
 */
public class FTPClientPool implements Closeable {
    private GenericObjectPool<FTPClient> ftpClientPool = null;

    public FTPClientPool(FTPConfig ftpConfig) throws IOException {
        GenericObjectPoolConfig<FTPClient> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(ftpConfig.getMaxTotal());
        config.setMinIdle(ftpConfig.getMinIdle());
        config.setMaxIdle(ftpConfig.getMaxIdle());
        config.setMaxWaitMillis(ftpConfig.getMaxWaitMillis());

        FTPClientFactory factory = new FTPClientFactory(ftpConfig);
        ftpClientPool = new GenericObjectPool<FTPClient>(factory, config);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public FTPClient getFtpClient() throws Exception {
        return ftpClientPool.borrowObject();
    }

    /**
     * 
     * @param ftpClient
     */
    public void returnObject(FTPClient ftpClient) {
        ftpClientPool.returnObject(ftpClient);
    }

    @Override
    public void close() throws IOException {
        ftpClientPool.close();
    }
}
