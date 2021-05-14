package awesome.ftp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author awesome
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FTPConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private Integer dataTimeout = 10 * 1000;
    private String basePath;
    private String encoding;

    private Integer maxTotal;
    private Integer minIdle;
    private Integer maxIdle;
    private Long maxWaitMillis;
}
