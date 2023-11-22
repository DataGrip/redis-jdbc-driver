package jdbc.client.query.structures;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.HostAndPort;

public class NodeHint {

    private final HostAndPort hostAndPort;

    public NodeHint(@NotNull HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    @NotNull
    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }
}
