package jdbc.client.helpers.result;

import jdbc.client.helpers.result.parser.ResultParser;
import jdbc.client.helpers.result.parser.ResultParserWrapper;
import jdbc.client.structures.query.CompositeCommand;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static jdbc.client.helpers.result.parser.ResultParserFactory.*;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrap;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrapList;
import static jdbc.client.structures.query.CompositeCommand.create;
import static jdbc.utils.Utils.length;
import static jdbc.utils.Utils.param;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    private static final Map<CompositeCommand, List<ResultParserWrapper>> RESULT_PARSERS = new HashMap<>() {{
        /* --------------------------------------------- Native --------------------------------------------- */
        put(create(Command.ACL, Keyword.CAT), wrapList(STRING));
        put(create(Command.ACL, Keyword.DELUSER), wrapList(LONG));
        put(create(Command.ACL, Keyword.DRYRUN), wrapList(STRING));
        put(create(Command.ACL, Keyword.GENPASS), wrapList(STRING));
        put(create(Command.ACL, Keyword.GETUSER), wrapList(ACCESS_CONTROL_USER));
        put(create(Command.ACL, Keyword.LIST), wrapList(STRING));
        put(create(Command.ACL, Keyword.LOAD), wrapList(STRING));
        put(create(Command.ACL, Keyword.LOG), wrapList(ACCESS_CONTROL_LOG_ENTRY));
        put(create(Command.ACL, Keyword.SAVE), wrapList(STRING));
        put(create(Command.ACL, Keyword.SETUSER), wrapList(STRING));
        put(create(Command.ACL, Keyword.USERS), wrapList(STRING));
        put(create(Command.ACL, Keyword.WHOAMI), wrapList(STRING));
        put(create(Command.APPEND), wrapList(LONG));
        put(create(Command.AUTH), wrapList(STRING));
        put(create(Command.BGREWRITEAOF), wrapList(STRING));
        put(create(Command.BGSAVE), wrapList(STRING));
        put(create(Command.BITCOUNT), wrapList(LONG));
        put(create(Command.BITFIELD), wrapList(LONG));
        put(create(Command.BITFIELD_RO), wrapList(LONG));
        put(create(Command.BITOP), wrapList(LONG));
        put(create(Command.BITPOS), wrapList(LONG));
        put(create(Command.BLMOVE), wrapList(STRING));
        put(create(Command.BLMPOP), wrapList(KEYED_STRING_LIST));
        put(create(Command.BLPOP), wrapList(KEYED_STRING));
        put(create(Command.BRPOP), wrapList(KEYED_STRING));
        put(create(Command.BRPOPLPUSH), wrapList(STRING));
        put(create(Command.BZMPOP), wrapList(KEYED_TUPLE_LIST));
        put(create(Command.BZPOPMAX), wrapList(KEYED_TUPLE));
        put(create(Command.BZPOPMIN), wrapList(KEYED_TUPLE));
        // put(create(Command.CLIENT, Keyword.CACHING), wrapList(STRING)); - Keyword.CACHING doesn't exist
        put(create(Command.CLIENT, Keyword.GETNAME), wrapList(STRING));
        // put(create(Command.CLIENT, Keyword.GETREDIR), wrapList(LONG)); - Keyword.GETREDIR doesn't exist
        put(create(Command.CLIENT, Keyword.ID), wrapList(LONG));
        put(create(Command.CLIENT, Keyword.INFO), wrapList(STRING));
        put(create(Command.CLIENT, Keyword.KILL), asList(wrap(STRING, length(3)), wrap(LONG)));
        put(create(Command.CLIENT, Keyword.LIST), wrapList(STRING));
        // put(create(Command.CLIENT, Keyword.NO-EVICT), wrapList(???)); - Keyword.NO-EVICT doesn't exist
        put(create(Command.CLIENT, Keyword.PAUSE), wrapList(STRING));
        // put(create(Command.CLIENT, Keyword.REPLY), wrapList(???)); - Keyword.REPLY doesn't exist
        put(create(Command.CLIENT, Keyword.SETNAME), wrapList(STRING));
        // put(create(Command.CLIENT, Keyword.TRACKING), wrapList(???)); - Keyword.TRACKING doesn't exist
        // put(create(Command.CLIENT, Keyword.TRACKINGINFO), wrapList(???)); - Keyword.TRACKINGINFO doesn't exist
        put(create(Command.CLIENT, Keyword.UNBLOCK), wrapList(LONG));
        // put(create(Command.CLIENT, Keyword.UNPAUSE), wrapList(???)); - Keyword.UNPAUSE doesn't exist
        // put(create(Command.COMMAND), wrapList(???)); - Is COMMAND without keyword allowed?
        put(create(Command.COMMAND, Keyword.COUNT), wrapList(LONG));
        put(create(Command.COMMAND, Keyword.DOCS), wrapList(COMMAND_DOCUMENT));
        put(create(Command.COMMAND, Keyword.GETKEYS), wrapList(STRING));
        put(create(Command.COMMAND, Keyword.GETKEYSANDFLAGS), wrapList(KEYED_STRING_LIST));
        put(create(Command.COMMAND, Keyword.INFO), wrapList(COMMAND_INFO));
        put(create(Command.COMMAND, Keyword.LIST), wrapList(STRING));
        put(create(Command.CONFIG, Keyword.GET), wrapList(STRING_MAP));
        put(create(Command.CONFIG, Keyword.RESETSTAT), wrapList(STRING));
        put(create(Command.CONFIG, Keyword.REWRITE), wrapList(STRING));
        put(create(Command.CONFIG, Keyword.SET), wrapList(STRING));
        put(create(Command.COPY), wrapList(BOOLEAN));
        put(create(Command.DBSIZE), wrapList(LONG));
        put(create(Command.DECR), wrapList(LONG));
        put(create(Command.DECRBY), wrapList(LONG));
        put(create(Command.DEL), wrapList(LONG));
        put(create(Command.DISCARD), wrapList(STRING));
        put(create(Command.DUMP), wrapList(BYTE_ARRAY));
        put(create(Command.ECHO), wrapList(STRING));
        put(create(Command.EVAL), wrapList(OBJECT));
        put(create(Command.EVAL_RO), wrapList(OBJECT));
        put(create(Command.EVALSHA), wrapList(OBJECT));
        put(create(Command.EVAL_RO), wrapList(OBJECT));
        put(create(Command.EXEC), wrapList(OBJECT));
        put(create(Command.EXISTS), wrapList(LONG));
        put(create(Command.EXPIRE), wrapList(BOOLEAN));
        put(create(Command.EXPIREAT), wrapList(BOOLEAN));
        put(create(Command.EXPIRETIME), wrapList(LONG));
        put(create(Command.FCALL), wrapList(OBJECT));
        put(create(Command.FCALL_RO), wrapList(OBJECT));
        put(create(Command.FLUSHALL), wrapList(STRING));
        put(create(Command.FLUSHDB), wrapList(STRING));
        put(create(Command.FUNCTION, Keyword.DELETE), wrapList(STRING));
        put(create(Command.FUNCTION, Keyword.DUMP), wrapList(BYTE_ARRAY));
        put(create(Command.FUNCTION, Keyword.FLUSH), wrapList(STRING));
        put(create(Command.FUNCTION, Keyword.KILL), wrapList(STRING));
        put(create(Command.FUNCTION, Keyword.LIST), wrapList(LIBRARY_INFO));
        put(create(Command.FUNCTION, Keyword.LOAD), wrapList(STRING));
        // put(create(Command.FUNCTION, Keyword.RESTORE), wrapList(STRING)); - Keyword.RESTORE doesn't exist
        put(create(Command.FUNCTION, Keyword.STATS), wrapList(FUNCTION_STATS));
        put(create(Command.GEOADD), wrapList(LONG));
        put(create(Command.GEODIST), wrapList(DOUBLE));
        put(create(Command.GEOHASH), wrapList(STRING));
        put(create(Command.GEOPOS), wrapList(GEO_COORDINATE));
        put(create(Command.GEORADIUS), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUS_RO), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUSBYMEMBER), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUSBYMEMBER_RO), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEOSEARCH), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEOSEARCHSTORE), wrapList(LONG));
        put(create(Command.GET), wrapList(STRING));
        put(create(Command.GETBIT), wrapList(LONG));
        put(create(Command.GETDEL), wrapList(STRING));
        put(create(Command.GETEX), wrapList(STRING));
        put(create(Command.GETRANGE), wrapList(STRING));
        put(create(Command.GETSET), wrapList(STRING));
        put(create(Command.HDEL), wrapList(LONG));
        // put(create(Command.HELLO, wrapList(???)); - Command.HELLO doesn't exist
        put(create(Command.HEXISTS), wrapList(BOOLEAN));
        put(create(Command.HGET), wrapList(STRING));
        put(create(Command.HGETALL), wrapList(STRING_MAP));
        put(create(Command.HINCRBY), wrapList(LONG));
        put(create(Command.HINCRBYFLOAT), wrapList(DOUBLE));
        put(create(Command.HKEYS), wrapList(STRING));
        put(create(Command.HLEN), wrapList(LONG));
        put(create(Command.HRANDFIELD), asList(wrap(STRING_MAP, param(Keyword.WITHVALUES)), wrap(STRING)));
        put(create(Command.HSCAN), wrapList(ENTRY_SCAN_RESULT));
        put(create(Command.HSET), wrapList(LONG));
        put(create(Command.HSETNX), wrapList(BOOLEAN));
        put(create(Command.HSTRLEN), wrapList(LONG));
        put(create(Command.HVALS), wrapList(STRING));
        put(create(Command.INCR), wrapList(LONG));
        put(create(Command.INCRBY), wrapList(LONG));
        put(create(Command.INCRBYFLOAT), wrapList(DOUBLE));
        put(create(Command.INFO), wrapList(STRING));
        put(create(Command.KEYS), wrapList(STRING));
        put(create(Command.LASTSAVE), wrapList(LONG));
        // put(create(Command.LATENCY, ...), wrapList(...)); - Command.LATENCY doesn't exist
        // TODO: put(create(Command.LCS), wrapList(???)) - implement result parser: LCSMatchResult (MatchedPosition, Position)
        put(create(Command.LINDEX), wrapList(STRING));
        put(create(Command.LINSERT), wrapList(LONG));
        put(create(Command.LLEN), wrapList(LONG));
        put(create(Command.LMOVE), wrapList(STRING));
        put(create(Command.LMPOP), wrapList(KEYED_STRING_LIST));
        put(create(Command.LOLWUT), wrapList(STRING));
        put(create(Command.LPOP), wrapList(STRING));
        put(create(Command.LPOS), wrapList(LONG));
        put(create(Command.LPUSH), wrapList(LONG));
        put(create(Command.LPUSHX), wrapList(LONG));
        put(create(Command.LRANGE), wrapList(STRING));
        put(create(Command.LREM), wrapList(LONG));
        put(create(Command.LSET), wrapList(STRING));
        put(create(Command.LTRIM), wrapList(STRING));
        put(create(Command.MEMORY, Keyword.DOCTOR), wrapList(STRING));
        // put(create(Command.MEMORY, Keyword.MALLOC-STATS), wrapList(???)); - Keyword.MALLOC-STATS doesn't exist
        put(create(Command.MEMORY, Keyword.PURGE), wrapList(STRING));
        put(create(Command.MEMORY, Keyword.STATS), wrapList(OBJECT_MAP));
        put(create(Command.MEMORY, Keyword.USAGE), wrapList(LONG));
        put(create(Command.MGET), wrapList(STRING));
        put(create(Command.MIGRATE), wrapList(STRING));
        put(create(Command.MODULE, Keyword.LIST), wrapList(MODULE));
        put(create(Command.MODULE, Keyword.LOAD), wrapList(STRING));
        // put(create(Command.MODULE, Keyword.LOADEX), wrapList(???)); - Keyword.LOADEX doesn't exist
        put(create(Command.MODULE, Keyword.UNLOAD), wrapList(STRING));
        put(create(Command.MONITOR), wrapList(STRING));
        put(create(Command.MOVE), wrapList(BOOLEAN));
        put(create(Command.MSET), wrapList(STRING));
        put(create(Command.MSETNX), wrapList(BOOLEAN));
        put(create(Command.MULTI), wrapList(STRING));
        put(create(Command.OBJECT, Keyword.ENCODING), wrapList(STRING));
        put(create(Command.OBJECT, Keyword.FREQ), wrapList(LONG));
        put(create(Command.OBJECT, Keyword.IDLETIME), wrapList(LONG));
        put(create(Command.OBJECT, Keyword.REFCOUNT), wrapList(LONG));
        put(create(Command.PERSIST), wrapList(BOOLEAN));
        put(create(Command.PEXPIRE), wrapList(BOOLEAN));
        put(create(Command.PEXPIREAT), wrapList(BOOLEAN));
        put(create(Command.PEXPIRETIME), wrapList(LONG));
        put(create(Command.PFADD), wrapList(BOOLEAN));
        put(create(Command.PFCOUNT), wrapList(LONG));
        // put(create(Command.PFDEBUG), wrapList(???)); - Command.PFDEBUG doesn't exist
        put(create(Command.PFMERGE), wrapList(STRING));
        // put(create(Command.PFSELFTEST), wrapList(???)); - Command.PFSELFTEST doesn't exist
        put(create(Command.PING), wrapList(STRING));
        put(create(Command.PSETEX), wrapList(STRING));
        // put(create(Command.PSYNC), wrapList(???)); - Command.PSYNC doesn't exist
        put(create(Command.PTTL), wrapList(LONG));
        put(create(Command.PUBLISH), wrapList(LONG));
        put(create(Command.PUBSUB, Keyword.CHANNELS), wrapList(STRING));
        put(create(Command.PUBSUB, Keyword.NUMPAT), wrapList(LONG));
        // TODO: put(create(Command.PUBSUB, Keyword.NUMSUB), wrapList(LONG_MAP)); -- implement result parser: LONG_MAP
        // put(create(Command.PUBSUB, Keyword.SHARDCHANNELS), wrapList(???)); - Keyword.SHARDCHANNELS doesn't exist
        // put(create(Command.PUBSUB, Keyword.SHARDNUMSUB), wrapList(???)); - Keyword.SHARDNUMSUB doesn't exist
        put(create(Command.QUIT), wrapList(STRING));
        put(create(Command.RANDOMKEY), wrapList(STRING));
        put(create(Command.RENAME), wrapList(STRING));
        put(create(Command.RENAMENX), wrapList(BOOLEAN));
        // put(create(Command.REPLCONF), wrapList(???)); - Command.REPLCONF doesn't exist
        put(create(Command.REPLICAOF), wrapList(STRING));
        // put(create(Command.RESET), wrapList(???)); - Command.RESET doesn't exist
        put(create(Command.RESTORE), wrapList(STRING));
        put(create(Command.ROLE), wrapList(OBJECT));
        put(create(Command.RPOP), wrapList(STRING));
        put(create(Command.RPOPLPUSH), wrapList(STRING));
        put(create(Command.RPUSH), wrapList(LONG));
        put(create(Command.RPUSHX), wrapList(LONG));
        put(create(Command.SADD), wrapList(LONG));
        put(create(Command.SCAN), wrapList(STRING_SCAN_RESULT));
        put(create(Command.SCARD), wrapList(LONG));
        // put(create(Command.SCRIPT, Keyword.DEBUG), wrapList(???));  - Keyword.DEBUG doesn't exist
        put(create(Command.SCRIPT, Keyword.EXISTS), wrapList(BOOLEAN));
        put(create(Command.SCRIPT, Keyword.FLUSH), wrapList(STRING));
        put(create(Command.SCRIPT, Keyword.KILL), wrapList(STRING));
        put(create(Command.SCRIPT, Keyword.LOAD), wrapList(STRING));
        put(create(Command.SDIFF), wrapList(STRING));
        put(create(Command.SDIFFSTORE), wrapList(LONG));
        put(create(Command.SET), wrapList(STRING));
        put(create(Command.SETBIT), wrapList(LONG));
        put(create(Command.SETEX), wrapList(STRING));
        put(create(Command.SETNX), wrapList(BOOLEAN));
        put(create(Command.SETRANGE), wrapList(LONG));
        put(create(Command.SHUTDOWN), wrapList(STRING));
        put(create(Command.SINTER), wrapList(STRING));
        put(create(Command.SINTERCARD), wrapList(LONG));
        put(create(Command.SINTERSTORE), wrapList(LONG));
        put(create(Command.SISMEMBER), wrapList(BOOLEAN));
        put(create(Command.SLAVEOF), wrapList(STRING));
        put(create(Command.SLOWLOG, Keyword.GET), wrapList(SLOW_LOG));
        put(create(Command.SLOWLOG, Keyword.LEN), wrapList(LONG));
        put(create(Command.SLOWLOG, Keyword.RESET), wrapList(STRING));
        put(create(Command.SMEMBERS), wrapList(STRING));
        put(create(Command.SMISMEMBER), wrapList(BOOLEAN));
        put(create(Command.SMOVE), wrapList(BOOLEAN));
        put(create(Command.SORT), asList(wrap(LONG, param(Keyword.STORE)), wrap(STRING)));
        put(create(Command.SORT_RO), wrapList(STRING));
        put(create(Command.SPOP), wrapList(STRING));
        put(create(Command.SRANDMEMBER), wrapList(STRING));
        put(create(Command.SREM), wrapList(LONG));
        put(create(Command.SSCAN), wrapList(STRING_SCAN_RESULT));
        put(create(Command.STRLEN), wrapList(LONG));
        put(create(Command.SUBSTR), wrapList(STRING));
        put(create(Command.SUNION), wrapList(STRING));
        put(create(Command.SUNIONSTORE), wrapList(LONG));
        put(create(Command.SWAPDB), wrapList(STRING));
        // put(create(Command.SYNC), wrapList(???)); - Command.SYNC doesn't exist
        put(create(Command.TIME), wrapList(STRING));
        put(create(Command.TOUCH), wrapList(LONG));
        put(create(Command.TTL), wrapList(LONG));
        put(create(Command.TYPE), wrapList(STRING));
        put(create(Command.UNLINK), wrapList(LONG));
        put(create(Command.UNWATCH), wrapList(STRING));
        put(create(Command.WAIT), wrapList(LONG));
        put(create(Command.WATCH), wrapList(STRING));
        put(create(Command.XACK), wrapList(LONG));
        put(create(Command.XADD), wrapList(STREAM_ENTRY_ID));
        // TODO: put(create(Command.XAUTOCLAIM), wrapList(???)); - implement result parsers
        put(create(Command.XCLAIM), wrapList(STREAM_ENTRY));
        put(create(Command.XDEL), wrapList(LONG));
        put(create(Command.XGROUP, Keyword.CREATE), wrapList(STRING));
        put(create(Command.XGROUP, Keyword.CREATECONSUMER), wrapList(BOOLEAN));
        put(create(Command.XGROUP, Keyword.DELCONSUMER), wrapList(LONG));
        put(create(Command.XGROUP, Keyword.DESTROY), wrapList(LONG));
        put(create(Command.XGROUP, Keyword.SETID), wrapList(STRING));
        put(create(Command.XINFO, Keyword.CONSUMERS), wrapList(STREAM_CONSUMER_INFO));
        put(create(Command.XINFO, Keyword.GROUPS), wrapList(STREAM_GROUP_INFO));
        put(create(Command.XINFO, Keyword.STREAM), asList(wrap(STREAM_INFO_FULL, param(Keyword.FULL)), wrap(STREAM_INFO)));
        put(create(Command.XLEN), wrapList(LONG));
        put(create(Command.XPENDING), asList(wrap(STREAM_PENDING_SUMMARY, length(3)), wrap(STREAM_PENDING_ENTRY)));
        put(create(Command.XRANGE), wrapList(STREAM_ENTRY));
        put(create(Command.XREAD), wrapList(STREAM_READ));
        put(create(Command.XREADGROUP), wrapList(STREAM_READ));
        put(create(Command.XREVRANGE), wrapList(STREAM_ENTRY));
        // put(create(Command.XSETID), wrapList(???)); - Command.XSETID doesn't exist
        put(create(Command.XTRIM), wrapList(LONG));
        put(create(Command.ZADD), wrapList(LONG));
        put(create(Command.ZCARD), wrapList(LONG));
        put(create(Command.ZCOUNT), wrapList(LONG));
        put(create(Command.ZDIFF), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZDIFFSTORE), wrapList(LONG));
        put(create(Command.ZINCRBY), wrapList(DOUBLE));
        put(create(Command.ZINTER), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZINTERCARD), wrapList(LONG));
        put(create(Command.ZINTERSTORE), wrapList(LONG));
        put(create(Command.ZLEXCOUNT), wrapList(LONG));
        put(create(Command.ZMPOP), wrapList(KEYED_TUPLE_LIST));
        put(create(Command.ZMSCORE), wrapList(DOUBLE));
        put(create(Command.ZPOPMAX), wrapList(TUPLE));
        put(create(Command.ZPOPMIN), wrapList(TUPLE));
        put(create(Command.ZRANDMEMBER), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZRANGE), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZRANGEBYLEX), wrapList(STRING));
        put(create(Command.ZRANGEBYSCORE), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZRANK), wrapList(LONG));
        put(create(Command.ZREM), wrapList(LONG));
        put(create(Command.ZREMRANGEBYRANK), wrapList(LONG));
        put(create(Command.ZREMRANGEBYSCORE), wrapList(LONG));
        put(create(Command.ZREVRANGE), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZREVRANGEBYLEX), wrapList(STRING));
        put(create(Command.ZREVRANGEBYSCORE), asList(wrap(TUPLE, param(Keyword.WITHSCORES)), wrap(STRING)));
        put(create(Command.ZREVRANK), wrapList(LONG));
        put(create(Command.ZSCAN), wrapList(TUPLE_SCAN_RESULT));
        put(create(Command.ZSCORE), wrapList(DOUBLE));
        /* --------------------------------------------- RedisJSON --------------------------------------------- */
        put(create(JsonCommand.ARRAPPEND), wrapList(LONG));
        put(create(JsonCommand.ARRINDEX), wrapList(LONG));
        put(create(JsonCommand.ARRINSERT), wrapList(LONG));
        put(create(JsonCommand.ARRLEN), wrapList(LONG));
        put(create(JsonCommand.ARRPOP), wrapList(JSON_OBJECT));
        put(create(JsonCommand.ARRTRIM), wrapList(LONG));
        put(create(JsonCommand.CLEAR), wrapList(LONG));
        // TODO (unknown keyword): put(create(JsonCommand.DEBUG /*, MEMORY */), wrapList(LONG));
        put(create(JsonCommand.DEL), wrapList(LONG));
        // TODO (unknown): put(create(JsonCommand.FORGET), wrapList(LONG));
        put(create(JsonCommand.GET), wrapList(JSON_OBJECT));
        // TODO (unknown): put(create(JsonCommand.MERGE), wrapList(STRING));
        put(create(JsonCommand.MGET), wrapList(JSON_OBJECT));
        // TODO (unknown): put(create(JsonCommand.MSET), wrapList(STRING));
        // TODO (parser): put(create(JsonCommand.NUMINCRBY), wrapList());
        // TODO (unknown): put(create(JsonCommand.NUMMULTBY), wrapList());
        // TODO (parser): put(create(JsonCommand.OBJKEYS), wrapList());
        // TODO (parser): put(create(JsonCommand.RESP), wrapList());
        put(create(JsonCommand.SET), wrapList(STRING));
        put(create(JsonCommand.STRAPPEND), wrapList(LONG));
        put(create(JsonCommand.STRLEN), wrapList(LONG));
        put(create(JsonCommand.TOGGLE), wrapList(BOOLEAN));
        put(create(JsonCommand.TYPE), wrapList(STRING));
    }};

    private static @NotNull ResultParser getResultParser(@NotNull RedisQuery query) {
        List<ResultParserWrapper> wrappers = RESULT_PARSERS.get(query.getCompositeCommand());
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
