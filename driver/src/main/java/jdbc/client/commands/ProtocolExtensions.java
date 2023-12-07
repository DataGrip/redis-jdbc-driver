package jdbc.client.commands;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

public class ProtocolExtensions {

    /* ------------------------------------------------- Native ------------------------------------------------- */

    public enum CommandEx implements ProtocolCommand {
        HELLO, REPLCONF, RESET, RESTOREASKING("RESTORE-ASKING"), PFDEBUG, PFSELFTEST,
        PSYNC, SYNC, XSETID;

        private final byte[] raw;

        CommandEx() {
            raw = SafeEncoder.encode(name());
        }

        CommandEx(@NotNull String alt) {
            raw = SafeEncoder.encode(alt);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }

    enum KeywordEx implements Rawable {
        CACHING, GETREDIR, NOEVICT("NO-EVICT"), REPLY, TRACKING, NOTOUCH("NO-TOUCH"),
        TRACKINGINFO, RESTORE, GRAPH, HISTOGRAM, HISTORY, LATEST, MALLOCSTATS("MALLOC-STATS"),
        SHARDCHANNELS, SHARDNUMSUB, DEBUG;

        private final byte[] raw;

        KeywordEx() {
            raw = SafeEncoder.encode(name());
        }

        KeywordEx(@NotNull String alt) {
            raw = SafeEncoder.encode(alt);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }

    enum ClusterKeywordEx implements Rawable {
        COUNTFAILUREREPORT("COUNT-FAILURE-REPORT"), SETCONFIGEPOCH("SET-CONFIG-EPOCH"), SHARDS;

        private final byte[] raw;

        ClusterKeywordEx() {
            raw = SafeEncoder.encode(name());
        }

        ClusterKeywordEx(@NotNull String alt) {
            raw = SafeEncoder.encode(alt);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }

    /* ----------------------------------------------- RedisJSON ------------------------------------------------ */

    public enum JsonCommandEx implements ProtocolCommand {
        DEBUG("JSON.DEBUG"),
        FORGET("JSON.FORGET"),
        MERGE("JSON.MERGE"),
        MSET("JSON.MSET"),
        NUMMULTBY("JSON.NUMMULTBY");

        private final byte[] raw;

        JsonCommandEx(@NotNull String alt) {
            raw = SafeEncoder.encode(alt);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }

    enum JsonKeywordEx implements Rawable {
        MEMORY;

        private final byte[] raw;

        JsonKeywordEx() {
            raw = SafeEncoder.encode(name());
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }

    /* ---------------------------------------------------------------------------------------------------------- */

}
