package jdbc.client.helpers.query.parser;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.ClusterKeyword;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.Protocol.SentinelKeyword;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

class KeywordParser {

    private KeywordParser() {
    }


    private static final Map<String, Keyword> KEYWORDS = toMap(Keyword.values());
    private static final Map<String, ClusterKeyword> CLUSTER_KEYWORDS = toMap(ClusterKeyword.values());
    private static final Map<String, SentinelKeyword> SENTINEL_KEYWORDS = toMap(SentinelKeyword.values());


    public static @NotNull Rawable parseKeyword(@NotNull ProtocolCommand command, @NotNull String keyword) {
        String keywordName = Utils.getName(keyword);
        Rawable knownKeyword = parseKnownKeyword(command, keywordName);
        return knownKeyword != null ? knownKeyword : new UnknownKeyword(keywordName);
    }

    private static @Nullable Rawable parseKnownKeyword(@NotNull ProtocolCommand command, @NotNull String keywordName) {
        if (command == Command.CLUSTER) return CLUSTER_KEYWORDS.get(keywordName);
        if (command == Command.SENTINEL) return SENTINEL_KEYWORDS.get(keywordName);
        return KEYWORDS.get(keywordName);
    }


    private static class UnknownKeyword implements ProtocolCommand {
        private final byte[] raw;

        UnknownKeyword(@NotNull String keywordName) {
            raw = SafeEncoder.encode(keywordName);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }
}
