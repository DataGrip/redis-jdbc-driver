package jdbc.client;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static jdbc.properties.RedisDefaultConfig.CONFIG;
import static jdbc.properties.RedisDriverPropertyInfoHelper.MAX_ATTEMPTS;

class RedisJedisClusterURI extends RedisJedisURIBase {

    private static final String PREFIX = "jdbc:redis:cluster://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }

    @Override
    protected @NotNull String getPrefix() {
        return PREFIX;
    }


    // host and port
    private Set<HostAndPort> nodes;

    // parameters
    private int maxAttempts;

    public RedisJedisClusterURI(String url, Properties info) {
        super(url, info);
    }


    @Override
    protected void setHostAndPort(@NotNull String nodesBlock) {
        Set<HostAndPort> nodes = new HashSet<>();

        String[] nodesParts = nodesBlock.split(",");
        for (String nodeBlock : nodesParts) {
            String host = Protocol.DEFAULT_HOST;
            int port = Protocol.DEFAULT_PORT;

            if (!nodeBlock.isEmpty()) {
                String[] nodeParts = nodeBlock.split(":", 2);
                if (nodeParts.length == 1) {
                    host = nodeParts[0];
                } else {
                    host = nodeParts[0];
                    port = Integer.parseInt(nodeParts[1]);
                }
            }

            nodes.add(new HostAndPort(host, port));
        }

        this.nodes = nodes;
    }

    @Override
    protected void setParameters(@NotNull Map<String, String> parameters, @Nullable Properties info) {
        super.setParameters(parameters, info);
        this.maxAttempts = getInt(parameters, info, MAX_ATTEMPTS, CONFIG.getMaxAttempts());
    }


    public Set<HostAndPort> getNodes() {
        return nodes;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
