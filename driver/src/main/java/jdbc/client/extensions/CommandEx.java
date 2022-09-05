package jdbc.client.extensions;

import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

/* redis.clients.jedis.Protocol.Command */
public enum CommandEx implements ProtocolCommand {
    ;

    private final byte[] raw;

    CommandEx() {
        raw = SafeEncoder.encode(this.name());
    }

    @Override
    public byte[] getRaw() {
        return raw;
    }
}
