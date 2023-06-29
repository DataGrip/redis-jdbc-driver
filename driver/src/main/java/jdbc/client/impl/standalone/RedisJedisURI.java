package jdbc.client.impl.standalone;

import jdbc.client.impl.RedisJedisURIBase;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

import java.sql.SQLException;
import java.util.Properties;

import static jdbc.utils.Utils.adjustHost;

public class RedisJedisURI extends RedisJedisURIBase {

    public static final String PREFIX = "jdbc:redis://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }

    @Override
    protected @NotNull String getPrefix() {
        return PREFIX;
    }


    // host and port
    private HostAndPort hostAndPort;


    public RedisJedisURI(String url, Properties info) throws SQLException {
        super(url, info);
    }


    @Override
    protected void setHostAndPort(@NotNull String hostAndPortBlock) {
        String host = Protocol.DEFAULT_HOST;
        int port = Protocol.DEFAULT_PORT;

        if (!hostAndPortBlock.isEmpty()) {
            String[] hostAndPortParts = hostAndPortBlock.split(":", 2);
            if (hostAndPortParts.length == 1) {
                host = hostAndPortParts[0];
            } else {
                host = hostAndPortParts[0];
                port = Integer.parseInt(hostAndPortParts[1]);
            }
        }

        this.hostAndPort = new HostAndPort(adjustHost(host), port);
    }


    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }
}
