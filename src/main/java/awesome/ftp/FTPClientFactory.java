package awesome.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import lombok.AllArgsConstructor;

/**
 * 
 * @author awesome
 */
@AllArgsConstructor
public class FTPClientFactory implements PooledObjectFactory<FTPClient> {

    private FTPConfig ftpConfig;

    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        return new DefaultPooledObject<FTPClient>(new FTPClient());
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<FTPClient> p) throws Exception {
        connect(p.getObject(), ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(),
                ftpConfig.getPassword(), ftpConfig.getDataTimeout(), ftpConfig.getEncoding(), ftpConfig.getBasePath());
    }

    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.changeWorkingDirectory(ftpConfig.getBasePath());
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    static void connect(FTPClient ftpClient, String host, int port, String username, String password, int dataTimeout,
            String encoding, String basePath) throws IOException {
        ftpClient.connect(host, port);
        if (ftpClient.login(username, password)) {
            ftpClient.setDataTimeout(dataTimeout);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding(encoding);
            ftpClient.changeWorkingDirectory(basePath);
            ftpClient.enterLocalPassiveMode();
        } else {
            ftpClient.disconnect();
            throw new IOException("connect failed");
        }
    }
}