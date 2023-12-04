package jdbc.client.result.parser;

import jdbc.client.commands.RedisCommand;
import jdbc.client.commands.RedisCommands;
import jdbc.client.query.structures.Params;
import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.search.SearchProtocol.SearchKeyword;

import java.util.HashMap;
import java.util.function.Predicate;

import static jdbc.client.result.parser.ResultParserFactory.*;
import static jdbc.client.result.parser.ResultParsers.ResultParserWrapper.wrap;
import static jdbc.utils.Utils.contains;
import static jdbc.utils.Utils.length;

public class ResultParsers {

    private ResultParsers() {
    }


    private static final CommandResultParsersMap CRP_MAP = new CommandResultParsersMap();

    static {
        
        /* --------------------------------------------- Native --------------------------------------------- */

        CRP_MAP.put(RedisCommands.ACL_CAT,                 STRING);
        CRP_MAP.put(RedisCommands.ACL_DELUSER,             LONG);
        CRP_MAP.put(RedisCommands.ACL_DRYRUN,              STRING);
        CRP_MAP.put(RedisCommands.ACL_GENPASS,             STRING);
        CRP_MAP.put(RedisCommands.ACL_GETUSER,             ACCESS_CONTROL_USER);
        CRP_MAP.put(RedisCommands.ACL_LIST,                STRING);
        CRP_MAP.put(RedisCommands.ACL_LOAD,                STRING);
        CRP_MAP.put(RedisCommands.ACL_LOG,                 ACCESS_CONTROL_LOG_ENTRY);
        CRP_MAP.put(RedisCommands.ACL_SAVE,                STRING);
        CRP_MAP.put(RedisCommands.ACL_SETUSER,             STRING);
        CRP_MAP.put(RedisCommands.ACL_USERS,               STRING);
        CRP_MAP.put(RedisCommands.ACL_WHOAMI,              STRING);
        CRP_MAP.put(RedisCommands.APPEND,                  LONG);
        CRP_MAP.put(RedisCommands.AUTH,                    STRING);
        CRP_MAP.put(RedisCommands.BGREWRITEAOF,            STRING);
        CRP_MAP.put(RedisCommands.BGSAVE,                  STRING);
        CRP_MAP.put(RedisCommands.BITCOUNT,                LONG);
        CRP_MAP.put(RedisCommands.BITFIELD,                LONG);
        CRP_MAP.put(RedisCommands.BITFIELD_RO,             LONG);
        CRP_MAP.put(RedisCommands.BITOP,                   LONG);
        CRP_MAP.put(RedisCommands.BITPOS,                  LONG);
        CRP_MAP.put(RedisCommands.BLMOVE,                  STRING);
        CRP_MAP.put(RedisCommands.BLMPOP,                  KEYED_STRING_LIST);
        CRP_MAP.put(RedisCommands.BLPOP,                   KEYED_STRING);
        CRP_MAP.put(RedisCommands.BRPOP,                   KEYED_STRING);
        CRP_MAP.put(RedisCommands.BRPOPLPUSH,              STRING);
        CRP_MAP.put(RedisCommands.BZMPOP,                  KEYED_TUPLE_LIST);
        CRP_MAP.put(RedisCommands.BZPOPMAX,                KEYED_TUPLE);
        CRP_MAP.put(RedisCommands.BZPOPMIN,                KEYED_TUPLE);
        CRP_MAP.put(RedisCommands.CLIENT_CACHING,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_GETNAME,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_GETREDIR,         LONG);
        CRP_MAP.put(RedisCommands.CLIENT_ID,               LONG);
        CRP_MAP.put(RedisCommands.CLIENT_INFO,             STRING);
        CRP_MAP.put(RedisCommands.CLIENT_KILL,             LONG, wrap(STRING, length(2)));
        CRP_MAP.put(RedisCommands.CLIENT_LIST,             STRING);
        CRP_MAP.put(RedisCommands.CLIENT_NOEVICT,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_NOTOUCH,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_PAUSE,            STRING);
        CRP_MAP.put(RedisCommands.CLIENT_REPLY,            STRING);
        CRP_MAP.put(RedisCommands.CLIENT_SETINFO,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_SETNAME,          STRING);
        CRP_MAP.put(RedisCommands.CLIENT_TRACKING,         STRING);
        CRP_MAP.put(RedisCommands.CLIENT_TRACKINGINFO,     OBJECT_MAP);
        CRP_MAP.put(RedisCommands.CLIENT_UNBLOCK,          LONG);
        CRP_MAP.put(RedisCommands.CLIENT_UNPAUSE,          STRING);
        CRP_MAP.put(RedisCommands.COMMAND,                 COMMAND_INFO);
        CRP_MAP.put(RedisCommands.COMMAND_COUNT,           LONG);
        CRP_MAP.put(RedisCommands.COMMAND_DOCS,            COMMAND_DOCUMENT);
        CRP_MAP.put(RedisCommands.COMMAND_GETKEYS,         STRING);
        CRP_MAP.put(RedisCommands.COMMAND_GETKEYSANDFLAGS, KEYED_STRING_LIST);
        CRP_MAP.put(RedisCommands.COMMAND_INFO,            COMMAND_INFO);
        CRP_MAP.put(RedisCommands.COMMAND_LIST,            STRING);
        CRP_MAP.put(RedisCommands.CONFIG_GET,              STRING_MAP);
        CRP_MAP.put(RedisCommands.CONFIG_RESETSTAT,        STRING);
        CRP_MAP.put(RedisCommands.CONFIG_REWRITE,          STRING);
        CRP_MAP.put(RedisCommands.CONFIG_SET,              STRING);
        CRP_MAP.put(RedisCommands.COPY,                    BOOLEAN);
        CRP_MAP.put(RedisCommands.DBSIZE,                  LONG);
        CRP_MAP.put(RedisCommands.DECR,                    LONG);
        CRP_MAP.put(RedisCommands.DECRBY,                  LONG);
        CRP_MAP.put(RedisCommands.DEL,                     LONG);
        CRP_MAP.put(RedisCommands.DISCARD,                 STRING);
        CRP_MAP.put(RedisCommands.DUMP,                    BYTE_ARRAY);
        CRP_MAP.put(RedisCommands.ECHO,                    STRING);
        CRP_MAP.put(RedisCommands.EVAL,                    OBJECT);
        CRP_MAP.put(RedisCommands.EVAL_RO,                 OBJECT);
        CRP_MAP.put(RedisCommands.EVALSHA,                 OBJECT);
        CRP_MAP.put(RedisCommands.EVALSHA_RO,              OBJECT);
        CRP_MAP.put(RedisCommands.EXEC,                    OBJECT);
        CRP_MAP.put(RedisCommands.EXISTS,                  LONG);
        CRP_MAP.put(RedisCommands.EXPIRE,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.EXPIREAT,                BOOLEAN);
        CRP_MAP.put(RedisCommands.EXPIRETIME,              LONG);
        CRP_MAP.put(RedisCommands.FCALL,                   OBJECT);
        CRP_MAP.put(RedisCommands.FCALL_RO,                OBJECT);
        CRP_MAP.put(RedisCommands.FLUSHALL,                STRING);
        CRP_MAP.put(RedisCommands.FLUSHDB,                 STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_DELETE,         STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_DUMP,           BYTE_ARRAY);
        CRP_MAP.put(RedisCommands.FUNCTION_FLUSH,          STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_KILL,           STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_LIST,           LIBRARY_INFO);
        CRP_MAP.put(RedisCommands.FUNCTION_LOAD,           STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_RESTORE,        STRING);
        CRP_MAP.put(RedisCommands.FUNCTION_STATS,          FUNCTION_STATS);
        CRP_MAP.put(RedisCommands.GEOADD,                  LONG);
        CRP_MAP.put(RedisCommands.GEODIST,                 DOUBLE);
        CRP_MAP.put(RedisCommands.GEOHASH,                 STRING);
        CRP_MAP.put(RedisCommands.GEOPOS,                  GEO_COORDINATE);
        CRP_MAP.put(RedisCommands.GEORADIUS,               GEORADIUS_RESPONSE);
        CRP_MAP.put(RedisCommands.GEORADIUS_RO,            GEORADIUS_RESPONSE);
        CRP_MAP.put(RedisCommands.GEORADIUSBYMEMBER,       GEORADIUS_RESPONSE);
        CRP_MAP.put(RedisCommands.GEORADIUSBYMEMBER_RO,    GEORADIUS_RESPONSE);
        CRP_MAP.put(RedisCommands.GEOSEARCH,               GEORADIUS_RESPONSE);
        CRP_MAP.put(RedisCommands.GEOSEARCHSTORE,          LONG);
        CRP_MAP.put(RedisCommands.GET,                     STRING);
        CRP_MAP.put(RedisCommands.GETBIT,                  LONG);
        CRP_MAP.put(RedisCommands.GETDEL,                  STRING);
        CRP_MAP.put(RedisCommands.GETEX,                   STRING);
        CRP_MAP.put(RedisCommands.GETRANGE,                STRING);
        CRP_MAP.put(RedisCommands.GETSET,                  STRING);
        CRP_MAP.put(RedisCommands.HDEL,                    LONG);
        CRP_MAP.put(RedisCommands.HELLO,                   OBJECT_MAP);
        CRP_MAP.put(RedisCommands.HEXISTS,                 BOOLEAN);
        CRP_MAP.put(RedisCommands.HGET,                    STRING);
        CRP_MAP.put(RedisCommands.HGETALL,                 STRING_MAP);
        CRP_MAP.put(RedisCommands.HINCRBY,                 LONG);
        CRP_MAP.put(RedisCommands.HINCRBYFLOAT,            DOUBLE);
        CRP_MAP.put(RedisCommands.HKEYS,                   STRING);
        CRP_MAP.put(RedisCommands.HLEN,                    LONG);
        CRP_MAP.put(RedisCommands.HRANDFIELD,              STRING, wrap(STRING_MAP, contains(Keyword.WITHVALUES)));
        CRP_MAP.put(RedisCommands.HSCAN,                   ENTRY_SCAN_RESULT);
        CRP_MAP.put(RedisCommands.HSET,                    LONG);
        CRP_MAP.put(RedisCommands.HSETNX,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.HSTRLEN,                 LONG);
        CRP_MAP.put(RedisCommands.HVALS,                   STRING);
        CRP_MAP.put(RedisCommands.INCR,                    LONG);
        CRP_MAP.put(RedisCommands.INCRBY,                  LONG);
        CRP_MAP.put(RedisCommands.INCRBYFLOAT,             DOUBLE);
        CRP_MAP.put(RedisCommands.INFO,                    STRING);
        CRP_MAP.put(RedisCommands.KEYS,                    STRING);
        CRP_MAP.put(RedisCommands.LASTSAVE,                LONG);
        CRP_MAP.put(RedisCommands.LATENCY_DOCTOR,          STRING);
        CRP_MAP.put(RedisCommands.LATENCY_GRAPH,           RAW_OBJECT); /* missed */
        CRP_MAP.put(RedisCommands.LATENCY_HISTOGRAM,       RAW_OBJECT); /* missed */
        CRP_MAP.put(RedisCommands.LATENCY_HISTORY,         RAW_OBJECT); /* missed */
        CRP_MAP.put(RedisCommands.LATENCY_LATEST,          RAW_OBJECT); /* missed */
        CRP_MAP.put(RedisCommands.LATENCY_RESET,           RAW_OBJECT); /* missed */
        CRP_MAP.put(RedisCommands.LCS,                     LCS_MATCH_RESULT);
        CRP_MAP.put(RedisCommands.LINDEX,                  STRING);
        CRP_MAP.put(RedisCommands.LINSERT,                 LONG);
        CRP_MAP.put(RedisCommands.LLEN,                    LONG);
        CRP_MAP.put(RedisCommands.LMOVE,                   STRING);
        CRP_MAP.put(RedisCommands.LMPOP,                   KEYED_STRING_LIST);
        CRP_MAP.put(RedisCommands.LOLWUT,                  STRING);
        CRP_MAP.put(RedisCommands.LPOP,                    STRING);
        CRP_MAP.put(RedisCommands.LPOS,                    LONG);
        CRP_MAP.put(RedisCommands.LPUSH,                   LONG);
        CRP_MAP.put(RedisCommands.LPUSHX,                  LONG);
        CRP_MAP.put(RedisCommands.LRANGE,                  STRING);
        CRP_MAP.put(RedisCommands.LREM,                    LONG);
        CRP_MAP.put(RedisCommands.LSET,                    STRING);
        CRP_MAP.put(RedisCommands.LTRIM,                   STRING);
        CRP_MAP.put(RedisCommands.MEMORY_DOCTOR,           STRING);
        CRP_MAP.put(RedisCommands.MEMORY_MALLOCSTATS,      STRING);
        CRP_MAP.put(RedisCommands.MEMORY_PURGE,            STRING);
        CRP_MAP.put(RedisCommands.MEMORY_STATS,            OBJECT_MAP);
        CRP_MAP.put(RedisCommands.MEMORY_USAGE,            LONG);
        CRP_MAP.put(RedisCommands.MGET,                    STRING);
        CRP_MAP.put(RedisCommands.MIGRATE,                 STRING);
        CRP_MAP.put(RedisCommands.MODULE_LIST,             MODULE);
        CRP_MAP.put(RedisCommands.MODULE_LOAD,             STRING);
        CRP_MAP.put(RedisCommands.MODULE_LOADEX,           STRING);
        CRP_MAP.put(RedisCommands.MODULE_UNLOAD,           STRING);
        CRP_MAP.put(RedisCommands.MONITOR,                 STRING);
        CRP_MAP.put(RedisCommands.MOVE,                    BOOLEAN);
        CRP_MAP.put(RedisCommands.MSET,                    STRING);
        CRP_MAP.put(RedisCommands.MSETNX,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.MULTI,                   STRING);
        CRP_MAP.put(RedisCommands.OBJECT_ENCODING,         STRING);
        CRP_MAP.put(RedisCommands.OBJECT_FREQ,             LONG);
        CRP_MAP.put(RedisCommands.OBJECT_IDLETIME,         LONG);
        CRP_MAP.put(RedisCommands.OBJECT_REFCOUNT,         LONG);
        CRP_MAP.put(RedisCommands.PERSIST,                 BOOLEAN);
        CRP_MAP.put(RedisCommands.PEXPIRE,                 BOOLEAN);
        CRP_MAP.put(RedisCommands.PEXPIREAT,               BOOLEAN);
        CRP_MAP.put(RedisCommands.PEXPIRETIME,             LONG);
        CRP_MAP.put(RedisCommands.PFADD,                   BOOLEAN);
        CRP_MAP.put(RedisCommands.PFCOUNT,                 LONG);
        CRP_MAP.put(RedisCommands.PFDEBUG,                 RAW_OBJECT); /* internal */
        CRP_MAP.put(RedisCommands.PFMERGE,                 STRING);
        CRP_MAP.put(RedisCommands.PFSELFTEST,              RAW_OBJECT); /* internal */
        CRP_MAP.put(RedisCommands.PING,                    STRING);
        CRP_MAP.put(RedisCommands.PSETEX,                  STRING);
        CRP_MAP.put(RedisCommands.PSYNC,                   STRING);
        CRP_MAP.put(RedisCommands.PTTL,                    LONG);
        CRP_MAP.put(RedisCommands.PUBLISH,                 LONG);
        CRP_MAP.put(RedisCommands.PUBSUB_CHANNELS,         STRING);
        CRP_MAP.put(RedisCommands.PUBSUB_NUMPAT,           LONG);
        CRP_MAP.put(RedisCommands.PUBSUB_NUMSUB,           PUBSUB_NUMSUB_RESPONSE);
        CRP_MAP.put(RedisCommands.PUBSUB_SHARDCHANNELS,    STRING);
        CRP_MAP.put(RedisCommands.PUBSUB_SHARDNUMSUB,      PUBSUB_NUMSUB_RESPONSE);
        CRP_MAP.put(RedisCommands.RANDOMKEY,               STRING);
        CRP_MAP.put(RedisCommands.READONLY,                STRING);
        CRP_MAP.put(RedisCommands.READWRITE,               STRING);
        CRP_MAP.put(RedisCommands.RENAME,                  STRING);
        CRP_MAP.put(RedisCommands.RENAMENX,                BOOLEAN);
        CRP_MAP.put(RedisCommands.REPLCONF,                STRING);
        CRP_MAP.put(RedisCommands.REPLICAOF,               STRING);
        CRP_MAP.put(RedisCommands.RESET,                   STRING);
        CRP_MAP.put(RedisCommands.RESTORE,                 STRING);
        CRP_MAP.put(RedisCommands.RESTOREASKING,           STRING);
        CRP_MAP.put(RedisCommands.ROLE,                    OBJECT);
        CRP_MAP.put(RedisCommands.RPOP,                    STRING);
        CRP_MAP.put(RedisCommands.RPOPLPUSH,               STRING);
        CRP_MAP.put(RedisCommands.RPUSH,                   LONG);
        CRP_MAP.put(RedisCommands.RPUSHX,                  LONG);
        CRP_MAP.put(RedisCommands.SADD,                    LONG);
        CRP_MAP.put(RedisCommands.SAVE,                    STRING);
        CRP_MAP.put(RedisCommands.SCAN,                    STRING_SCAN_RESULT);
        CRP_MAP.put(RedisCommands.SCARD,                   LONG);
        CRP_MAP.put(RedisCommands.SCRIPT_DEBUG,            STRING);
        CRP_MAP.put(RedisCommands.SCRIPT_EXISTS,           BOOLEAN);
        CRP_MAP.put(RedisCommands.SCRIPT_FLUSH,            STRING);
        CRP_MAP.put(RedisCommands.SCRIPT_KILL,             STRING);
        CRP_MAP.put(RedisCommands.SCRIPT_LOAD,             STRING);
        CRP_MAP.put(RedisCommands.SDIFF,                   STRING);
        CRP_MAP.put(RedisCommands.SDIFFSTORE,              LONG);
        CRP_MAP.put(RedisCommands.SELECT,                  STRING); // TODO: ???
        CRP_MAP.put(RedisCommands.SET,                     STRING);
        CRP_MAP.put(RedisCommands.SETBIT,                  LONG);
        CRP_MAP.put(RedisCommands.SETEX,                   STRING);
        CRP_MAP.put(RedisCommands.SETNX,                   BOOLEAN);
        CRP_MAP.put(RedisCommands.SETRANGE,                LONG);
        CRP_MAP.put(RedisCommands.SHUTDOWN,                STRING);
        CRP_MAP.put(RedisCommands.SINTER,                  STRING);
        CRP_MAP.put(RedisCommands.SINTERCARD,              LONG);
        CRP_MAP.put(RedisCommands.SINTERSTORE,             LONG);
        CRP_MAP.put(RedisCommands.SISMEMBER,               BOOLEAN);
        CRP_MAP.put(RedisCommands.SLAVEOF,                 STRING);
        CRP_MAP.put(RedisCommands.SLOWLOG_GET,             SLOW_LOG);
        CRP_MAP.put(RedisCommands.SLOWLOG_LEN,             LONG);
        CRP_MAP.put(RedisCommands.SLOWLOG_RESET,           STRING);
        CRP_MAP.put(RedisCommands.SMEMBERS,                STRING);
        CRP_MAP.put(RedisCommands.SMISMEMBER,              BOOLEAN);
        CRP_MAP.put(RedisCommands.SMOVE,                   BOOLEAN);
        CRP_MAP.put(RedisCommands.SORT,                    STRING, wrap(LONG, contains(Keyword.STORE)));
        CRP_MAP.put(RedisCommands.SORT_RO,                 STRING);
        CRP_MAP.put(RedisCommands.SPOP,                    STRING);
        CRP_MAP.put(RedisCommands.SRANDMEMBER,             STRING);
        CRP_MAP.put(RedisCommands.SREM,                    LONG);
        CRP_MAP.put(RedisCommands.SSCAN,                   STRING_SCAN_RESULT);
        CRP_MAP.put(RedisCommands.STRLEN,                  LONG);
        CRP_MAP.put(RedisCommands.SUBSTR,                  STRING);
        CRP_MAP.put(RedisCommands.SUNION,                  STRING);
        CRP_MAP.put(RedisCommands.SUNIONSTORE,             LONG);
        CRP_MAP.put(RedisCommands.SWAPDB,                  STRING);
        CRP_MAP.put(RedisCommands.SYNC,                    STRING);
        CRP_MAP.put(RedisCommands.TIME,                    STRING);
        CRP_MAP.put(RedisCommands.TOUCH,                   LONG);
        CRP_MAP.put(RedisCommands.TTL,                     LONG);
        CRP_MAP.put(RedisCommands.TYPE,                    STRING);
        CRP_MAP.put(RedisCommands.UNLINK,                  LONG);
        CRP_MAP.put(RedisCommands.UNWATCH,                 STRING);
        CRP_MAP.put(RedisCommands.WAIT,                    LONG);
        // TODO: CRP_MAP.put(RedisCommands.WAITAOF,                 ???);
        CRP_MAP.put(RedisCommands.WATCH,                   STRING);
        CRP_MAP.put(RedisCommands.XACK,                    LONG);
        CRP_MAP.put(RedisCommands.XADD,                    STREAM_ENTRY_ID);
        // TODO: CRP_MAP.put(RedisCommands.XAUTOCLAIM,              ???); - implement result parsers
        CRP_MAP.put(RedisCommands.XCLAIM,                  STREAM_ENTRY);
        CRP_MAP.put(RedisCommands.XDEL,                    LONG);
        CRP_MAP.put(RedisCommands.XGROUP_CREATE,           STRING);
        CRP_MAP.put(RedisCommands.XGROUP_CREATECONSUMER,   BOOLEAN);
        CRP_MAP.put(RedisCommands.XGROUP_DELCONSUMER,      LONG);
        CRP_MAP.put(RedisCommands.XGROUP_DESTROY,          LONG);
        CRP_MAP.put(RedisCommands.XGROUP_SETID,            STRING);
        CRP_MAP.put(RedisCommands.XINFO_CONSUMERS,         STREAM_CONSUMER_INFO);
        CRP_MAP.put(RedisCommands.XINFO_GROUPS,            STREAM_GROUP_INFO);
        CRP_MAP.put(RedisCommands.XINFO_STREAM,            STREAM_INFO, wrap(STREAM_INFO_FULL, contains(Keyword.FULL)));
        CRP_MAP.put(RedisCommands.XLEN,                    LONG);
        CRP_MAP.put(RedisCommands.XPENDING,                STREAM_PENDING_ENTRY, wrap(STREAM_PENDING_SUMMARY, length(2)));
        CRP_MAP.put(RedisCommands.XRANGE,                  STREAM_ENTRY);
        CRP_MAP.put(RedisCommands.XREAD,                   STREAM_READ);
        CRP_MAP.put(RedisCommands.XREADGROUP,              STREAM_READ);
        CRP_MAP.put(RedisCommands.XREVRANGE,               STREAM_ENTRY);
        CRP_MAP.put(RedisCommands.XSETID,                  RAW_OBJECT); /* internal */
        CRP_MAP.put(RedisCommands.XTRIM,                   LONG);
        CRP_MAP.put(RedisCommands.ZADD,                    LONG);
        CRP_MAP.put(RedisCommands.ZCARD,                   LONG);
        CRP_MAP.put(RedisCommands.ZCOUNT,                  LONG);
        CRP_MAP.put(RedisCommands.ZDIFF,                   STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZDIFFSTORE,              LONG);
        CRP_MAP.put(RedisCommands.ZINCRBY,                 DOUBLE);
        CRP_MAP.put(RedisCommands.ZINTER,                  STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZINTERCARD,              LONG);
        CRP_MAP.put(RedisCommands.ZINTERSTORE,             LONG);
        CRP_MAP.put(RedisCommands.ZLEXCOUNT,               LONG);
        CRP_MAP.put(RedisCommands.ZMPOP,                   KEYED_TUPLE_LIST);
        CRP_MAP.put(RedisCommands.ZMSCORE,                 DOUBLE);
        CRP_MAP.put(RedisCommands.ZPOPMAX,                 TUPLE);
        CRP_MAP.put(RedisCommands.ZPOPMIN,                 TUPLE);
        CRP_MAP.put(RedisCommands.ZRANDMEMBER,             STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZRANGE,                  STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZRANGEBYLEX,             STRING);
        CRP_MAP.put(RedisCommands.ZRANGEBYSCORE,           STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZRANK,                   LONG);
        CRP_MAP.put(RedisCommands.ZREM,                    LONG);
        CRP_MAP.put(RedisCommands.ZREMRANGEBYLEX,          LONG);
        CRP_MAP.put(RedisCommands.ZREMRANGEBYRANK,         LONG);
        CRP_MAP.put(RedisCommands.ZREMRANGEBYSCORE,        LONG);
        CRP_MAP.put(RedisCommands.ZREVRANGE,               STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZREVRANGEBYLEX,          STRING);
        CRP_MAP.put(RedisCommands.ZREVRANGEBYSCORE,        STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZREVRANK,                LONG);
        CRP_MAP.put(RedisCommands.ZSCAN,                   TUPLE_SCAN_RESULT);
        CRP_MAP.put(RedisCommands.ZSCORE,                  DOUBLE);
        CRP_MAP.put(RedisCommands.ZUNION,                  STRING, wrap(TUPLE, contains(Keyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.ZUNIONSTORE,             LONG);

        CRP_MAP.put(RedisCommands.CLUSTER_ADDSLOTS,        STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_ADDSLOTSRANGE,   STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_BUMPEPOCH,       STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_COUNTFAILUREREPORT, LONG);
        CRP_MAP.put(RedisCommands.CLUSTER_COUNTKEYSINSLOT, LONG);
        CRP_MAP.put(RedisCommands.CLUSTER_DELSLOTS,        STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_DELSLOTSRANGE,   STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_FAILOVER,        STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_FLUSHSLOTS,      STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_FORGET,          STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_GETKEYSINSLOT,   STRING_LIST);
        CRP_MAP.put(RedisCommands.CLUSTER_INFO,            STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_KEYSLOT,         LONG);
        CRP_MAP.put(RedisCommands.CLUSTER_LINKS,           OBJECT_MAP);
        CRP_MAP.put(RedisCommands.CLUSTER_MEET,            STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_MYID,            STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_MYSHARDID,       STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_NODES,           STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_REPLICAS,        STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_REPLICATE,       STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_RESET,           STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_SAVECONFIG,      STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_SETCONFIGEPOCH,  STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_SETSLOT,         STRING);
        // TODO: CRP_MAP.put(RedisCommands.CLUSTER_SHARDS,          ???); // TODO: ????
        CRP_MAP.put(RedisCommands.CLUSTER_SLAVES,          STRING);
        CRP_MAP.put(RedisCommands.CLUSTER_SLOTS,           RAW_OBJECT); /* deprecated */

        /* --------------------------------------------- RedisJSON --------------------------------------------- */

        CRP_MAP.put(RedisCommands.JSON_ARRAPPEND,          LONG);
        CRP_MAP.put(RedisCommands.JSON_ARRINDEX,           LONG);
        CRP_MAP.put(RedisCommands.JSON_ARRINSERT,          LONG);
        CRP_MAP.put(RedisCommands.JSON_ARRLEN,             LONG);
        CRP_MAP.put(RedisCommands.JSON_ARRPOP,             JSON_OBJECT);
        CRP_MAP.put(RedisCommands.JSON_ARRTRIM,            LONG);
        CRP_MAP.put(RedisCommands.JSON_CLEAR,              LONG);
        CRP_MAP.put(RedisCommands.JSON_DEBUG_MEMORY,       LONG);
        CRP_MAP.put(RedisCommands.JSON_DEL,                LONG);
        CRP_MAP.put(RedisCommands.JSON_FORGET,             LONG);
        CRP_MAP.put(RedisCommands.JSON_GET,                JSON_OBJECT);
        CRP_MAP.put(RedisCommands.JSON_MERGE,              STRING);
        CRP_MAP.put(RedisCommands.JSON_MGET,               JSON_OBJECT);
        CRP_MAP.put(RedisCommands.JSON_MSET,               STRING);
        CRP_MAP.put(RedisCommands.JSON_NUMINCRBY,          JSON_OBJECT);
        CRP_MAP.put(RedisCommands.JSON_NUMMULTBY,          JSON_OBJECT);
        CRP_MAP.put(RedisCommands.JSON_OBJKEYS,            STRING_LIST);
        CRP_MAP.put(RedisCommands.JSON_OBJLEN,             LONG);
        CRP_MAP.put(RedisCommands.JSON_RESP,               OBJECT_LIST);
        CRP_MAP.put(RedisCommands.JSON_SET,                STRING);
        CRP_MAP.put(RedisCommands.JSON_STRAPPEND,          LONG);
        CRP_MAP.put(RedisCommands.JSON_STRLEN,             LONG);
        CRP_MAP.put(RedisCommands.JSON_TOGGLE,             BOOLEAN);
        CRP_MAP.put(RedisCommands.JSON_TYPE,               STRING);

        /* --------------------------------------------- RediSearch --------------------------------------------- */

        CRP_MAP.put(RedisCommands.FT__LIST,                STRING);
        CRP_MAP.put(RedisCommands.FT_AGGREGATE,            AGGREGATION_RESULT);
        CRP_MAP.put(RedisCommands.FT_ALIASADD,             STRING);
        CRP_MAP.put(RedisCommands.FT_ALIASDEL,             STRING);
        CRP_MAP.put(RedisCommands.FT_ALIASUPDATE,          STRING);
        CRP_MAP.put(RedisCommands.FT_ALTER,                STRING);
        CRP_MAP.put(RedisCommands.FT_CONFIG_GET,           STRING_MAP);
        CRP_MAP.put(RedisCommands.FT_CONFIG_SET,           STRING);
        CRP_MAP.put(RedisCommands.FT_CREATE,               STRING);
        CRP_MAP.put(RedisCommands.FT_CURSOR_DEL,           STRING);
        CRP_MAP.put(RedisCommands.FT_CURSOR_READ,          AGGREGATION_RESULT);
        CRP_MAP.put(RedisCommands.FT_DICTADD,              LONG);
        CRP_MAP.put(RedisCommands.FT_DICTDEL,              LONG);
        CRP_MAP.put(RedisCommands.FT_DICTDUMP,             STRING);
        CRP_MAP.put(RedisCommands.FT_DROPINDEX,            STRING);
        CRP_MAP.put(RedisCommands.FT_EXPLAIN,              STRING);
        CRP_MAP.put(RedisCommands.FT_EXPLAINCLI,           STRING);
        CRP_MAP.put(RedisCommands.FT_INFO,                 OBJECT_MAP);
        CRP_MAP.put(RedisCommands.FT_PROFILE,              wrap(AGGREGATION_PROFILE_RESPONSE, contains(SearchKeyword.AGGREGATE)),
                                                           wrap(SEARCH_PROFILE_RESPONSE, contains(SearchKeyword.SEARCH)));
        CRP_MAP.put(RedisCommands.FT_SEARCH,               SEARCH_RESULT);
        CRP_MAP.put(RedisCommands.FT_SPELLCHECK,           SEARCH_SPELLCHECK_RESPONSE);
        CRP_MAP.put(RedisCommands.FT_SUGADD,               LONG);
        CRP_MAP.put(RedisCommands.FT_SUGDEL,               BOOLEAN);
        CRP_MAP.put(RedisCommands.FT_SUGGET,               STRING, wrap(TUPLE, contains(SearchKeyword.WITHSCORES)));
        CRP_MAP.put(RedisCommands.FT_SUGLEN,               LONG);
        CRP_MAP.put(RedisCommands.FT_SYNDUMP,              SEARCH_SYNONYM_GROUPS);
        CRP_MAP.put(RedisCommands.FT_SYNUPDATE,            STRING);
        CRP_MAP.put(RedisCommands.FT_TAGVALS,              STRING);

        /* --------------------------------------------- RedisBloom --------------------------------------------- */

        CRP_MAP.put(RedisCommands.BF_ADD,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.BF_CARD,                 LONG);
        CRP_MAP.put(RedisCommands.BF_EXISTS,               BOOLEAN);
        CRP_MAP.put(RedisCommands.BF_INFO,                 OBJECT_MAP);
        CRP_MAP.put(RedisCommands.BF_INSERT,               BOOLEAN);
        CRP_MAP.put(RedisCommands.BF_LOADCHUNK,            STRING);
        CRP_MAP.put(RedisCommands.BF_MADD,                 BOOLEAN);
        CRP_MAP.put(RedisCommands.BF_MEXISTS,              BOOLEAN);
        CRP_MAP.put(RedisCommands.BF_RESERVE,              STRING);
        CRP_MAP.put(RedisCommands.BF_SCANDUMP,             BLOOM_SCANDUMP_RESPONSE);

        CRP_MAP.put(RedisCommands.CF_ADD,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_ADDNX,                BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_COUNT,                LONG);
        CRP_MAP.put(RedisCommands.CF_DEL,                  BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_EXISTS,               BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_INFO,                 OBJECT_MAP);
        CRP_MAP.put(RedisCommands.CF_INSERT,               BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_INSERTNX,             BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_LOADCHUNK,            STRING);
        CRP_MAP.put(RedisCommands.CF_MEXISTS,              BOOLEAN);
        CRP_MAP.put(RedisCommands.CF_RESERVE,              STRING);
        CRP_MAP.put(RedisCommands.CF_SCANDUMP,             BLOOM_SCANDUMP_RESPONSE);

        CRP_MAP.put(RedisCommands.CMS_INCRBY,              LONG);
        CRP_MAP.put(RedisCommands.CMS_INFO,                OBJECT_MAP);
        CRP_MAP.put(RedisCommands.CMS_INITBYDIM,           STRING);
        CRP_MAP.put(RedisCommands.CMS_INITBYPROB,          STRING);
        CRP_MAP.put(RedisCommands.CMS_MERGE,               STRING);
        CRP_MAP.put(RedisCommands.CMS_QUERY,               LONG);

        CRP_MAP.put(RedisCommands.TDIGEST_ADD,             STRING);
        CRP_MAP.put(RedisCommands.TDIGEST_BYRUNK,          DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_BYREVRANK,       DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_CDF,             DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_CREATE,          STRING);
        CRP_MAP.put(RedisCommands.TDIGEST_INFO,            OBJECT_MAP);
        CRP_MAP.put(RedisCommands.TDIGEST_MAX,             DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_MERGE,           STRING);
        CRP_MAP.put(RedisCommands.TDIGEST_MIN,             DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_QUANTILE,        DOUBLE);
        CRP_MAP.put(RedisCommands.TDIGEST_RANK,            LONG);
        CRP_MAP.put(RedisCommands.TDIGEST_RESET,           STRING);
        CRP_MAP.put(RedisCommands.TDIGEST_REVRANK,         LONG);
        CRP_MAP.put(RedisCommands.TDIGEST_TRIMMED_MEAN,    DOUBLE);

        CRP_MAP.put(RedisCommands.TOPK_ADD,                STRING);
        CRP_MAP.put(RedisCommands.TOPK_COUNT,              LONG);
        CRP_MAP.put(RedisCommands.TOPK_INCRBY,             STRING);
        CRP_MAP.put(RedisCommands.TOPK_INFO,               OBJECT_MAP);
        CRP_MAP.put(RedisCommands.TOPK_LIST,               STRING);
        CRP_MAP.put(RedisCommands.TOPK_QUERY,              BOOLEAN);
        CRP_MAP.put(RedisCommands.TOPK_RESERVE,            STRING);

        /* --------------------------------------------- RedisTimeSeries --------------------------------------------- */

        CRP_MAP.put(RedisCommands.TS_ADD,                  LONG);
        CRP_MAP.put(RedisCommands.TS_ALTER,                STRING);
        CRP_MAP.put(RedisCommands.TS_CREATE,               STRING);
        CRP_MAP.put(RedisCommands.TS_CREATERULE,           STRING);
        CRP_MAP.put(RedisCommands.TS_DECRBY,               LONG);
        CRP_MAP.put(RedisCommands.TS_DEL,                  LONG);
        CRP_MAP.put(RedisCommands.TS_DELETERULE,           STRING);
        CRP_MAP.put(RedisCommands.TS_GET,                  TIMESERIES_ELEMENT);
        CRP_MAP.put(RedisCommands.TS_INCRBY,               LONG);
        CRP_MAP.put(RedisCommands.TS_INFO,                 TIMESERIES_INFO);
        CRP_MAP.put(RedisCommands.TS_MADD,                 LONG);
        CRP_MAP.put(RedisCommands.TS_MGET,                 TIMESERIES_MGET_RESPONSE);
        CRP_MAP.put(RedisCommands.TS_MRANGE,               TIMESERIES_MRANGE_RESPONSE);
        CRP_MAP.put(RedisCommands.TS_MREVRANGE,            TIMESERIES_MRANGE_RESPONSE);
        CRP_MAP.put(RedisCommands.TS_QUERYINDEX,           STRING);
        CRP_MAP.put(RedisCommands.TS_RANGE,                TIMESERIES_ELEMENT);
        CRP_MAP.put(RedisCommands.TS_REVRANGE,             TIMESERIES_ELEMENT);

        /* ------------------------------------------------------------------------------------------ */

    }


    public static @Nullable ResultParser get(@NotNull RedisQuery query) {
        CommandResultParsers commandResultParsers = CRP_MAP.get(query.getCommand());
        return commandResultParsers != null ? commandResultParsers.get(query.getParams()) : null;
    }

    public static @NotNull ResultParser getDefault() {
        return RAW_OBJECT;
    }


    private static class CommandResultParsersMap extends HashMap<RedisCommand, CommandResultParsers> {
        void put(@NotNull RedisCommand command,
                 @Nullable ResultParser defaultResultParser,
                 @NotNull ResultParserWrapper... resultParserWrappers) {
            put(command, new CommandResultParsers(defaultResultParser, resultParserWrappers));
        }

        void put(@NotNull RedisCommand command,
                 @NotNull ResultParserWrapper... resultParserWrappers) {
            put(command, null, resultParserWrappers);
        }
    }

    private static class CommandResultParsers {

        private final ResultParser defaultResultParser;
        private final ResultParserWrapper[] resultParserWrappers;

        CommandResultParsers(@Nullable ResultParser defaultResultParser,
                             @NotNull ResultParserWrapper... resultParserWrappers) {
            this.defaultResultParser = defaultResultParser;
            this.resultParserWrappers = resultParserWrappers;
        }

        private @Nullable ResultParser get(@NotNull Params params) {
            for (ResultParserWrapper resultParserWrapper : resultParserWrappers) {
                if (resultParserWrapper.isApplicable.test(params)) return resultParserWrapper.resultParser;
            }
            return defaultResultParser;
        }
    }

    static class ResultParserWrapper {

        final ResultParser resultParser;
        final Predicate<Params> isApplicable;

        private ResultParserWrapper(@NotNull ResultParser resultParser,
                                    @NotNull Predicate<Params> isApplicable) {
            this.resultParser = resultParser;
            this.isApplicable = isApplicable;
        }

        static ResultParserWrapper wrap(@NotNull ResultParser resultParser,
                                        @NotNull Predicate<Params> isApplicable) {
            return new ResultParserWrapper(resultParser, isApplicable);
        }
    }

}
