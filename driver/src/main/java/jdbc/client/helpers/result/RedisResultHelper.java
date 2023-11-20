package jdbc.client.helpers.result;

import jdbc.client.helpers.result.parser.ResultParser;
import jdbc.client.helpers.result.parser.ResultParserWrapper;
import jdbc.client.structures.RedisCommand;
import jdbc.client.structures.RedisCommands;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static jdbc.client.helpers.result.parser.ResultParserFactory.*;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrap;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrapList;
import static jdbc.utils.Utils.length;
import static jdbc.utils.Utils.param;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    private static final Map<RedisCommand, List<ResultParserWrapper>> RESULT_PARSERS = new HashMap<>() {{
        /* --------------------------------------------- Native --------------------------------------------- */
        put(RedisCommands.ACL_CAT, wrapList(STRING));
        put(RedisCommands.ACL_DELUSER, wrapList(LONG));
        put(RedisCommands.ACL_DRYRUN, wrapList(STRING));
        put(RedisCommands.ACL_GENPASS, wrapList(STRING));
        put(RedisCommands.ACL_GETUSER, wrapList(ACCESS_CONTROL_USER));
        put(RedisCommands.ACL_LIST, wrapList(STRING));
        put(RedisCommands.ACL_LOAD, wrapList(STRING));
        put(RedisCommands.ACL_LOG, wrapList(ACCESS_CONTROL_LOG_ENTRY));
        put(RedisCommands.ACL_SAVE, wrapList(STRING));
        put(RedisCommands.ACL_SETUSER, wrapList(STRING));
        put(RedisCommands.ACL_USERS, wrapList(STRING));
        put(RedisCommands.ACL_WHOAMI, wrapList(STRING));
        put(RedisCommands.APPEND, wrapList(LONG));
        put(RedisCommands.AUTH, wrapList(STRING));
        put(RedisCommands.BGREWRITEAOF, wrapList(STRING));
        put(RedisCommands.BGSAVE, wrapList(STRING));
        put(RedisCommands.BITCOUNT, wrapList(LONG));
        put(RedisCommands.BITFIELD, wrapList(LONG));
        put(RedisCommands.BITFIELD_RO, wrapList(LONG));
        put(RedisCommands.BITOP, wrapList(LONG));
        put(RedisCommands.BITPOS, wrapList(LONG));
        put(RedisCommands.BLMOVE, wrapList(STRING));
        put(RedisCommands.BLMPOP, wrapList(KEYED_STRING_LIST));
        put(RedisCommands.BLPOP, wrapList(KEYED_STRING));
        put(RedisCommands.BRPOP, wrapList(KEYED_STRING));
        put(RedisCommands.BRPOPLPUSH, wrapList(STRING));
        put(RedisCommands.BZMPOP, wrapList(KEYED_TUPLE_LIST));
        put(RedisCommands.BZPOPMAX, wrapList(KEYED_TUPLE));
        put(RedisCommands.BZPOPMIN, wrapList(KEYED_TUPLE));
        // put(RedisCommands.CLIENT_CACHING, wrapList(STRING)); - Keyword.CACHING doesn't exist
        put(RedisCommands.CLIENT_GETNAME, wrapList(STRING));
        // put(RedisCommands.CLIENT_GETREDIR, wrapList(LONG)); - Keyword.GETREDIR doesn't exist
        put(RedisCommands.CLIENT_ID, wrapList(LONG));
        put(RedisCommands.CLIENT_INFO, wrapList(STRING));
        put(RedisCommands.CLIENT_KILL, asList(wrap(STRING, length(3)), wrap(LONG)));
        put(RedisCommands.CLIENT_LIST, wrapList(STRING));
        // put(RedisCommands.CLIENT_NO-EVICT, wrapList(???)); - Keyword.NO-EVICT doesn't exist
        put(RedisCommands.CLIENT_PAUSE, wrapList(STRING));
        // put(RedisCommands.CLIENT_REPLY, wrapList(???)); - Keyword.REPLY doesn't exist
        put(RedisCommands.CLIENT_SETNAME, wrapList(STRING));
        // put(RedisCommands.CLIENT_TRACKING, wrapList(???)); - Keyword.TRACKING doesn't exist
        // put(RedisCommands.CLIENT_TRACKINGINFO, wrapList(???)); - Keyword.TRACKINGINFO doesn't exist
        put(RedisCommands.CLIENT_UNBLOCK, wrapList(LONG));
        // put(RedisCommands.CLIENT_UNPAUSE, wrapList(???)); - Keyword.UNPAUSE doesn't exist
        // put(RedisCommands.COMMAND, wrapList(???)); - Is COMMAND without keyword allowed?
        put(RedisCommands.COMMAND_COUNT, wrapList(LONG));
        put(RedisCommands.COMMAND_DOCS, wrapList(COMMAND_DOCUMENT));
        put(RedisCommands.COMMAND_GETKEYS, wrapList(STRING));
        put(RedisCommands.COMMAND_GETKEYSANDFLAGS, wrapList(KEYED_STRING_LIST));
        put(RedisCommands.COMMAND_INFO, wrapList(COMMAND_INFO));
        put(RedisCommands.COMMAND_LIST, wrapList(STRING));
        put(RedisCommands.CONFIG_GET, wrapList(STRING_MAP));
        put(RedisCommands.CONFIG_RESETSTAT, wrapList(STRING));
        put(RedisCommands.CONFIG_REWRITE, wrapList(STRING));
        put(RedisCommands.CONFIG_SET, wrapList(STRING));
        put(RedisCommands.COPY, wrapList(BOOLEAN));
        put(RedisCommands.DBSIZE, wrapList(LONG));
        put(RedisCommands.DECR, wrapList(LONG));
        put(RedisCommands.DECRBY, wrapList(LONG));
        put(RedisCommands.DEL, wrapList(LONG));
        put(RedisCommands.DISCARD, wrapList(STRING));
        put(RedisCommands.DUMP, wrapList(BYTE_ARRAY));
        put(RedisCommands.ECHO, wrapList(STRING));
        put(RedisCommands.EVAL, wrapList(OBJECT));
        put(RedisCommands.EVAL_RO, wrapList(OBJECT));
        put(RedisCommands.EVALSHA, wrapList(OBJECT));
        put(RedisCommands.EVALSHA_RO, wrapList(OBJECT));
        put(RedisCommands.EXEC, wrapList(OBJECT));
        put(RedisCommands.EXISTS, wrapList(LONG));
        put(RedisCommands.EXPIRE, wrapList(BOOLEAN));
        put(RedisCommands.EXPIREAT, wrapList(BOOLEAN));
        put(RedisCommands.EXPIRETIME, wrapList(LONG));
        put(RedisCommands.FCALL, wrapList(OBJECT));
        put(RedisCommands.FCALL_RO, wrapList(OBJECT));
        put(RedisCommands.FLUSHALL, wrapList(STRING));
        put(RedisCommands.FLUSHDB, wrapList(STRING));
        put(RedisCommands.FUNCTION_DELETE, wrapList(STRING));
        put(RedisCommands.FUNCTION_DUMP, wrapList(BYTE_ARRAY));
        put(RedisCommands.FUNCTION_FLUSH, wrapList(STRING));
        put(RedisCommands.FUNCTION_KILL, wrapList(STRING));
        put(RedisCommands.FUNCTION_LIST, wrapList(LIBRARY_INFO));
        put(RedisCommands.FUNCTION_LOAD, wrapList(STRING));
        // put(RedisCommands.FUNCTION_RESTORE, wrapList(STRING)); - Keyword.RESTORE doesn't exist
        put(RedisCommands.FUNCTION_STATS, wrapList(FUNCTION_STATS));
        put(RedisCommands.GEOADD, wrapList(LONG));
        put(RedisCommands.GEODIST, wrapList(DOUBLE));
        put(RedisCommands.GEOHASH, wrapList(STRING));
        put(RedisCommands.GEOPOS, wrapList(GEO_COORDINATE));
        put(RedisCommands.GEORADIUS, wrapList(GEORADIUS_RESPONSE));
        put(RedisCommands.GEORADIUS_RO, wrapList(GEORADIUS_RESPONSE));
        put(RedisCommands.GEORADIUSBYMEMBER, wrapList(GEORADIUS_RESPONSE));
        put(RedisCommands.GEORADIUSBYMEMBER_RO, wrapList(GEORADIUS_RESPONSE));
        put(RedisCommands.GEOSEARCH, wrapList(GEORADIUS_RESPONSE));
        put(RedisCommands.GEOSEARCHSTORE, wrapList(LONG));
        put(RedisCommands.GET, wrapList(STRING));
        put(RedisCommands.GETBIT, wrapList(LONG));
        put(RedisCommands.GETDEL, wrapList(STRING));
        put(RedisCommands.GETEX, wrapList(STRING));
        put(RedisCommands.GETRANGE, wrapList(STRING));
        put(RedisCommands.GETSET, wrapList(STRING));
        put(RedisCommands.HDEL, wrapList(LONG));
        // put(RedisCommands.HELLO, wrapList(???)); - Command.HELLO doesn't exist
        put(RedisCommands.HEXISTS, wrapList(BOOLEAN));
        put(RedisCommands.HGET, wrapList(STRING));
        put(RedisCommands.HGETALL, wrapList(STRING_MAP));
        put(RedisCommands.HINCRBY, wrapList(LONG));
        put(RedisCommands.HINCRBYFLOAT, wrapList(DOUBLE));
        put(RedisCommands.HKEYS, wrapList(STRING));
        put(RedisCommands.HLEN, wrapList(LONG));
        put(RedisCommands.HRANDFIELD, asList(wrap(STRING_MAP, param(Keyword.WITHVALUES)), wrap(STRING)));
        put(RedisCommands.HSCAN, wrapList(ENTRY_SCAN_RESULT));
        put(RedisCommands.HSET, wrapList(LONG));
        put(RedisCommands.HSETNX, wrapList(BOOLEAN));
        put(RedisCommands.HSTRLEN, wrapList(LONG));
        put(RedisCommands.HVALS, wrapList(STRING));
        put(RedisCommands.INCR, wrapList(LONG));
        put(RedisCommands.INCRBY, wrapList(LONG));
        put(RedisCommands.INCRBYFLOAT, wrapList(DOUBLE));
        put(RedisCommands.INFO, wrapList(STRING));
        put(RedisCommands.KEYS, wrapList(STRING));
        put(RedisCommands.LASTSAVE, wrapList(LONG));
        // put(RedisCommands.LATENCY, ..., wrapList(...)); - Command.LATENCY doesn't exist
        // TODO: put(RedisCommands.LCS, wrapList(???)) - implement result parser: LCSMatchResult (MatchedPosition, Position)
        put(RedisCommands.LINDEX, wrapList(STRING));
        put(RedisCommands.LINSERT, wrapList(LONG));
        put(RedisCommands.LLEN, wrapList(LONG));
        put(RedisCommands.LMOVE, wrapList(STRING));
        put(RedisCommands.LMPOP, wrapList(KEYED_STRING_LIST));
        put(RedisCommands.LOLWUT, wrapList(STRING));
        put(RedisCommands.LPOP, wrapList(STRING));
        put(RedisCommands.LPOS, wrapList(LONG));
        put(RedisCommands.LPUSH, wrapList(LONG));
        put(RedisCommands.LPUSHX, wrapList(LONG));
        put(RedisCommands.LRANGE, wrapList(STRING));
        put(RedisCommands.LREM, wrapList(LONG));
        put(RedisCommands.LSET, wrapList(STRING));
        put(RedisCommands.LTRIM, wrapList(STRING));
        put(RedisCommands.MEMORY_DOCTOR, wrapList(STRING));
        // put(RedisCommands.MEMORY_MALLOC-STATS, wrapList(???)); - Keyword.MALLOC-STATS doesn't exist
        put(RedisCommands.MEMORY_PURGE, wrapList(STRING));
        put(RedisCommands.MEMORY_STATS, wrapList(OBJECT_MAP));
        put(RedisCommands.MEMORY_USAGE, wrapList(LONG));
        put(RedisCommands.MGET, wrapList(STRING));
        put(RedisCommands.MIGRATE, wrapList(STRING));
        put(RedisCommands.MODULE_LIST, wrapList(MODULE));
        put(RedisCommands.MODULE_LOAD, wrapList(STRING));
        // put(RedisCommands.MODULE_LOADEX, wrapList(???)); - Keyword.LOADEX doesn't exist
        put(RedisCommands.MODULE_UNLOAD, wrapList(STRING));
        put(RedisCommands.MONITOR, wrapList(STRING));
        put(RedisCommands.MOVE, wrapList(BOOLEAN));
        put(RedisCommands.MSET, wrapList(STRING));
        put(RedisCommands.MSETNX, wrapList(BOOLEAN));
        put(RedisCommands.MULTI, wrapList(STRING));
        put(RedisCommands.OBJECT_ENCODING, wrapList(STRING));
        put(RedisCommands.OBJECT_FREQ, wrapList(LONG));
        put(RedisCommands.OBJECT_IDLETIME, wrapList(LONG));
        put(RedisCommands.OBJECT_REFCOUNT, wrapList(LONG));
        put(RedisCommands.PERSIST, wrapList(BOOLEAN));
        put(RedisCommands.PEXPIRE, wrapList(BOOLEAN));
        put(RedisCommands.PEXPIREAT, wrapList(BOOLEAN));
        put(RedisCommands.PEXPIRETIME, wrapList(LONG));
        put(RedisCommands.PFADD, wrapList(BOOLEAN));
        put(RedisCommands.PFCOUNT, wrapList(LONG));
        // put(RedisCommands.PFDEBUG, wrapList(???)); - Command.PFDEBUG doesn't exist
        put(RedisCommands.PFMERGE, wrapList(STRING));
        // put(RedisCommands.PFSELFTEST, wrapList(???)); - Command.PFSELFTEST doesn't exist
        put(RedisCommands.PING, wrapList(STRING));
        put(RedisCommands.PSETEX, wrapList(STRING));
        // put(RedisCommands.PSYNC, wrapList(???)); - Command.PSYNC doesn't exist
        put(RedisCommands.PTTL, wrapList(LONG));
        put(RedisCommands.PUBLISH, wrapList(LONG));
        put(RedisCommands.PUBSUB_CHANNELS, wrapList(STRING));
        put(RedisCommands.PUBSUB_NUMPAT, wrapList(LONG));
        // TODO: put(RedisCommands.PUBSUB_NUMSUB, wrapList(LONG_MAP)); -- implement result parser: LONG_MAP
        // put(RedisCommands.PUBSUB_SHARDCHANNELS, wrapList(???)); - Keyword.SHARDCHANNELS doesn't exist
        // put(RedisCommands.PUBSUB_SHARDNUMSUB, wrapList(???)); - Keyword.SHARDNUMSUB doesn't exist
        put(RedisCommands.QUIT, wrapList(STRING));
        put(RedisCommands.RANDOMKEY, wrapList(STRING));
        put(RedisCommands.RENAME, wrapList(STRING));
        put(RedisCommands.RENAMENX, wrapList(BOOLEAN));
        // put(RedisCommands.REPLCONF, wrapList(???)); - Command.REPLCONF doesn't exist
        put(RedisCommands.REPLICAOF, wrapList(STRING));
        // put(RedisCommands.RESET, wrapList(???)); - Command.RESET doesn't exist
        put(RedisCommands.RESTORE, wrapList(STRING));
        put(RedisCommands.ROLE, wrapList(OBJECT));
        put(RedisCommands.RPOP, wrapList(STRING));
        put(RedisCommands.RPOPLPUSH, wrapList(STRING));
        put(RedisCommands.RPUSH, wrapList(LONG));
        put(RedisCommands.RPUSHX, wrapList(LONG));
        put(RedisCommands.SADD, wrapList(LONG));
        put(RedisCommands.SCAN, wrapList(STRING_SCAN_RESULT));
        put(RedisCommands.SCARD, wrapList(LONG));
        // put(RedisCommands.SCRIPT_DEBUG, wrapList(???));  - Keyword.DEBUG doesn't exist
        put(RedisCommands.SCRIPT_EXISTS, wrapList(BOOLEAN));
        put(RedisCommands.SCRIPT_FLUSH, wrapList(STRING));
        put(RedisCommands.SCRIPT_KILL, wrapList(STRING));
        put(RedisCommands.SCRIPT_LOAD, wrapList(STRING));
        put(RedisCommands.SDIFF, wrapList(STRING));
        put(RedisCommands.SDIFFSTORE, wrapList(LONG));
        put(RedisCommands.SET, wrapList(STRING));
        put(RedisCommands.SETBIT, wrapList(LONG));
        put(RedisCommands.SETEX, wrapList(STRING));
        put(RedisCommands.SETNX, wrapList(BOOLEAN));
        put(RedisCommands.SETRANGE, wrapList(LONG));
        put(RedisCommands.SHUTDOWN, wrapList(STRING));
        put(RedisCommands.SINTER, wrapList(STRING));
        put(RedisCommands.SINTERCARD, wrapList(LONG));
        put(RedisCommands.SINTERSTORE, wrapList(LONG));
        put(RedisCommands.SISMEMBER, wrapList(BOOLEAN));
        put(RedisCommands.SLAVEOF, wrapList(STRING));
        put(RedisCommands.SLOWLOG_GET, wrapList(SLOW_LOG));
        put(RedisCommands.SLOWLOG_LEN, wrapList(LONG));
        put(RedisCommands.SLOWLOG_RESET, wrapList(STRING));
        put(RedisCommands.SMEMBERS, wrapList(STRING));
        put(RedisCommands.SMISMEMBER, wrapList(BOOLEAN));
        put(RedisCommands.SMOVE, wrapList(BOOLEAN));
        put(RedisCommands.SORT, asList(wrap(LONG, param(Keyword.STORE)), wrap(STRING)));
        put(RedisCommands.SORT_RO, wrapList(STRING));
        put(RedisCommands.SPOP, wrapList(STRING));
        put(RedisCommands.SRANDMEMBER, wrapList(STRING));
        put(RedisCommands.SREM, wrapList(LONG));
        put(RedisCommands.SSCAN, wrapList(STRING_SCAN_RESULT));
        put(RedisCommands.STRLEN, wrapList(LONG));
        put(RedisCommands.SUBSTR, wrapList(STRING));
        put(RedisCommands.SUNION, wrapList(STRING));
        put(RedisCommands.SUNIONSTORE, wrapList(LONG));
        put(RedisCommands.SWAPDB, wrapList(STRING));
        // put(RedisCommands.SYNC, wrapList(???)); - Command.SYNC doesn't exist
        put(RedisCommands.TIME, wrapList(STRING));
        put(RedisCommands.TOUCH, wrapList(LONG));
        put(RedisCommands.TTL, wrapList(LONG));
        put(RedisCommands.TYPE, wrapList(STRING));
        put(RedisCommands.UNLINK, wrapList(LONG));
        put(RedisCommands.UNWATCH, wrapList(STRING));
        put(RedisCommands.WAIT, wrapList(LONG));
        put(RedisCommands.WATCH, wrapList(STRING));
        put(RedisCommands.XACK, wrapList(LONG));
        put(RedisCommands.XADD, wrapList(STREAM_ENTRY_ID));
        // TODO: put(RedisCommands.XAUTOCLAIM, wrapList(???)); - implement result parsers
        put(RedisCommands.XCLAIM, wrapList(STREAM_ENTRY));
        put(RedisCommands.XDEL, wrapList(LONG));
        put(RedisCommands.XGROUP_CREATE, wrapList(STRING));
        put(RedisCommands.XGROUP_CREATECONSUMER, wrapList(BOOLEAN));
        put(RedisCommands.XGROUP_DELCONSUMER, wrapList(LONG));
        put(RedisCommands.XGROUP_DESTROY, wrapList(LONG));
        put(RedisCommands.XGROUP_SETID, wrapList(STRING));
        put(RedisCommands.XINFO_CONSUMERS, wrapList(STREAM_CONSUMER_INFO));
        put(RedisCommands.XINFO_GROUPS, wrapList(STREAM_GROUP_INFO));
        put(RedisCommands.XINFO_STREAM, asList(wrap(STREAM_INFO_FULL, param(Keyword.FULL)), wrap(STREAM_INFO)));
        put(RedisCommands.XLEN, wrapList(LONG));
        put(RedisCommands.XPENDING, asList(wrap(STREAM_PENDING_SUMMARY, length(3)), wrap(STREAM_PENDING_ENTRY)));
        put(RedisCommands.XRANGE, wrapList(STREAM_ENTRY));
        put(RedisCommands.XREAD, wrapList(STREAM_READ));
        put(RedisCommands.XREADGROUP, wrapList(STREAM_READ));
        put(RedisCommands.XREVRANGE, wrapList(STREAM_ENTRY));
        // put(RedisCommands.XSETID, wrapList(???)); - Command.XSETID doesn't exist
        put(RedisCommands.XTRIM, wrapList(LONG));
        put(RedisCommands.ZADD, wrapList(LONG));
        put(RedisCommands.ZCARD, wrapList(LONG));
        put(RedisCommands.ZCOUNT, wrapList(LONG));
        put(RedisCommands.ZDIFF, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZDIFFSTORE, wrapList(LONG));
        put(RedisCommands.ZINCRBY, wrapList(DOUBLE));
        put(RedisCommands.ZINTER, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZINTERCARD, wrapList(LONG));
        put(RedisCommands.ZINTERSTORE, wrapList(LONG));
        put(RedisCommands.ZLEXCOUNT, wrapList(LONG));
        put(RedisCommands.ZMPOP, wrapList(KEYED_TUPLE_LIST));
        put(RedisCommands.ZMSCORE, wrapList(DOUBLE));
        put(RedisCommands.ZPOPMAX, wrapList(TUPLE));
        put(RedisCommands.ZPOPMIN, wrapList(TUPLE));
        put(RedisCommands.ZRANDMEMBER, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZRANGE, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZRANGEBYLEX, wrapList(STRING));
        put(RedisCommands.ZRANGEBYSCORE, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZRANK, wrapList(LONG));
        put(RedisCommands.ZREM, wrapList(LONG));
        put(RedisCommands.ZREMRANGEBYRANK, wrapList(LONG));
        put(RedisCommands.ZREMRANGEBYSCORE, wrapList(LONG));
        put(RedisCommands.ZREVRANGE, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZREVRANGEBYLEX, wrapList(STRING));
        put(RedisCommands.ZREVRANGEBYSCORE, asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(RedisCommands.ZREVRANK, wrapList(LONG));
        put(RedisCommands.ZSCAN, wrapList(TUPLE_SCAN_RESULT));
        put(RedisCommands.ZSCORE, wrapList(DOUBLE));
        /* --------------------------------------------- RedisJSON --------------------------------------------- */
        put(RedisCommands.JSON_ARRAPPEND, wrapList(LONG));
        put(RedisCommands.JSON_ARRINDEX, wrapList(LONG));
        put(RedisCommands.JSON_ARRINSERT, wrapList(LONG));
        put(RedisCommands.JSON_ARRLEN, wrapList(LONG));
        put(RedisCommands.JSON_ARRPOP, wrapList(JSON_OBJECT));
        put(RedisCommands.JSON_ARRTRIM, wrapList(LONG));
        put(RedisCommands.JSON_CLEAR, wrapList(LONG));
        // TODO (unknown keyword): put(RedisCommands.JSON_DEBUG /*, MEMORY */, wrapList(LONG));
        put(RedisCommands.JSON_DEL, wrapList(LONG));
        // TODO (unknown): put(RedisCommands.JSON_FORGET, wrapList(LONG));
        put(RedisCommands.JSON_GET, wrapList(JSON_OBJECT));
        // TODO (unknown): put(RedisCommands.JSON_MERGE, wrapList(STRING));
        put(RedisCommands.JSON_MGET, wrapList(JSON_OBJECT));
        // TODO (unknown): put(RedisCommands.JSON_MSET, wrapList(STRING));
        // TODO (parser): put(RedisCommands.JSON_NUMINCRBY, wrapList());
        // TODO (unknown): put(RedisCommands.JSON_NUMMULTBY, wrapList());
        put(RedisCommands.JSON_OBJKEYS, wrapList(STRING_LIST));
        put(RedisCommands.JSON_RESP, wrapList(OBJECT_LIST));
        put(RedisCommands.JSON_SET, wrapList(STRING));
        put(RedisCommands.JSON_STRAPPEND, wrapList(LONG));
        put(RedisCommands.JSON_STRLEN, wrapList(LONG));
        put(RedisCommands.JSON_TOGGLE, wrapList(BOOLEAN));
        put(RedisCommands.JSON_TYPE, wrapList(STRING));
    }};

    private static @NotNull ResultParser getResultParser(@NotNull RedisQuery query) {
        List<ResultParserWrapper> wrappers = RESULT_PARSERS.get(query.getCommand());
        if (wrappers != null) {
            Optional<ResultParserWrapper> wrapper = wrappers.stream().filter(p -> p.isApplicable(query)).findFirst();
            if (wrapper.isPresent()) return wrapper.get().getResultParser();
        }
        return OBJECT;
    }

    public static @NotNull RedisResult parseResult(@NotNull RedisQuery query, @Nullable Object data) {
        return getResultParser(query).parse(query, data);
    }
}
