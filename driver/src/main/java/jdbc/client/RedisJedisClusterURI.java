package jdbc.client;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class RedisJedisClusterURI extends RedisJedisURIBase {

    private static final String PREFIX = "jdbc:redis:cluster://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }

    @Override
    protected String getPrefix() {
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
    protected void setHostAndPort(String nodesBlock) {
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
    protected void setParameters(Map<String, String> parameters, Properties info) {
        int maxAttempts = getIntOption(parameters, "maxAttempts", JedisCluster.DEFAULT_MAX_ATTEMPTS);

        this.maxAttempts = getIntOption(info, "maxAttempts", maxAttempts);
    }


    public Set<HostAndPort> getNodes() {
        return nodes;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
