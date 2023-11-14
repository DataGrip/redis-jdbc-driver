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

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class KeywordParser {

    private KeywordParser() {
    }


    private static final Map<String, Keyword> KEYWORDS =
            Arrays.stream(Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, ClusterKeyword> CLUSTER_KEYWORDS =
            Arrays.stream(ClusterKeyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, SentinelKeyword> SENTINEL_KEYWORDS =
            Arrays.stream(SentinelKeyword.values()).collect(Collectors.toMap(Enum::name, v -> v));


    // TODO (stack): think about nullable
    public static @Nullable Rawable parseKeyword(@NotNull ProtocolCommand command, @NotNull String keyword) {
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
