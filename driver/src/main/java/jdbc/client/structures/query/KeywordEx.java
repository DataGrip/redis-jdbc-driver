package jdbc.client.structures.query;

import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Locale;

/* redis.clients.jedis.Protocol.Keyword */
public enum KeywordEx implements Rawable {
    ;

    public final byte[] raw;

    KeywordEx() {
        raw = SafeEncoder.encode(this.name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public byte[] getRaw() {
        return raw;
    }
}
