package jdbc.client;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

import java.util.Properties;

class RedisJedisURI extends RedisJedisURIBase {

    private static final String PREFIX = "jdbc:redis://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }

    @Override
    protected @NotNull String getPrefix() {
        return PREFIX;
    }


    // host and port
    private HostAndPort hostAndPort;

    public RedisJedisURI(String url, Properties info) {
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

        this.hostAndPort = new HostAndPort(host, port);
    }


    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }
}
