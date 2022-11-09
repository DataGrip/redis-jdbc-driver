package jdbc.client.helpers.result;

import jdbc.client.helpers.result.parser.ResultParser;
import jdbc.client.helpers.result.parser.ResultParserFactory;
import jdbc.client.structures.query.CompositeCommand;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.util.HashMap;
import java.util.Map;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    private static final Map<CompositeCommand, ResultParser> RESULT_PARSERS = new HashMap<>() {{
        put(CompositeCommand.create(Command.ACL, Keyword.DELUSER), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ACL, Keyword.GETUSER), ResultParserFactory.ACCESS_CONTROL_USER);
        put(CompositeCommand.create(Command.ACL, Keyword.LOG), ResultParserFactory.ACCESS_CONTROL_LOG_ENTRY);
        put(CompositeCommand.create(Command.APPEND), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITCOUNT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITFIELD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITFIELD_RO), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITFIELD_RO), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITOP), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BITPOS), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.BLPOP), ResultParserFactory.KEYED_LIST_ELEMENT);
        put(CompositeCommand.create(Command.BRPOP), ResultParserFactory.KEYED_LIST_ELEMENT);
        put(CompositeCommand.create(Command.BZPOPMAX), ResultParserFactory.KEYED_ZSET_ELEMENT);
        put(CompositeCommand.create(Command.BZPOPMIN), ResultParserFactory.KEYED_ZSET_ELEMENT);
        put(CompositeCommand.create(Command.CLIENT, Keyword.ID), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.CLIENT, Keyword.UNBLOCK), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.CONFIG, Keyword.GET), ResultParserFactory.STRING_MAP);
        put(CompositeCommand.create(Command.COPY), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.DBSIZE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.DECR), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.DECRBY), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.DEL), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.DUMP), ResultParserFactory.BYTE_ARRAY);
        put(CompositeCommand.create(Command.EXISTS), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.EXPIRE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.EXPIREAT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.GEOADD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.GEODIST), ResultParserFactory.DOUBLE);
        put(CompositeCommand.create(Command.GEOPOS), ResultParserFactory.GEO_COORDINATE);
        put(CompositeCommand.create(Command.GEORADIUS), ResultParserFactory.GEORADIUS_RESPONSE);
        put(CompositeCommand.create(Command.GEORADIUS_RO), ResultParserFactory.GEORADIUS_RESPONSE);
        put(CompositeCommand.create(Command.GEORADIUSBYMEMBER), ResultParserFactory.GEORADIUS_RESPONSE);
        put(CompositeCommand.create(Command.GEORADIUSBYMEMBER_RO), ResultParserFactory.GEORADIUS_RESPONSE);
        put(CompositeCommand.create(Command.GETBIT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.HDEL), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.HEXISTS), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.HGETALL), ResultParserFactory.STRING_MAP);
        put(CompositeCommand.create(Command.HINCRBY), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.HINCRBYFLOAT), ResultParserFactory.DOUBLE);
        put(CompositeCommand.create(Command.HLEN), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.HRANDFIELD, Keyword.WITHVALUES), ResultParserFactory.STRING_MAP);
        // TODO: put(RedisCompositeCommand.create(Protocol.Command.HSCAN), ???); - Builder with ScanResult
        put(CompositeCommand.create(Command.HSET), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.HSETNX), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.HSTRLEN), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.INCR), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.INCRBY), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.INCRBYFLOAT), ResultParserFactory.DOUBLE);
        put(CompositeCommand.create(Command.LASTSAVE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LINSERT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LLEN), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LPOS), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LPUSH), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LPUSHX), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.LREM), ResultParserFactory.LONG);
        // TODO: put(RedisCompositeCommand.create(Protocol.Command.Memory, Protocol.Keyword.STATS), ResultParserFactory.STRING_MAP); - need KeywordEx
        put(CompositeCommand.create(Command.MEMORY, Keyword.USAGE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.MODULE, Keyword.LIST), ResultParserFactory.MODULE);
        put(CompositeCommand.create(Command.MOVE), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.MSETNX), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.OBJECT, Keyword.FREQ), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.OBJECT, Keyword.IDLETIME), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.OBJECT, Keyword.REFCOUNT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.PERSIST), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.PEXPIRE), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.PEXPIREAT), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.PFADD), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.PFCOUNT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.PTTL), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.PUBLISH), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.RENAMENX), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.RPUSH), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.RPUSHX), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SADD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SCAN), ResultParserFactory.STRING_SCAN_RESULT);
        put(CompositeCommand.create(Command.SCARD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SCRIPT, Keyword.EXISTS), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.SDIFFSTORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SETBIT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SETNX), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.SETRANGE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SINTERSTORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SISMEMBER), ResultParserFactory.BOOLEAN);
        // TODO: put(RedisCompositeCommand.create(Command.SLOWLOG, Keyword.GET), ResultParserFactory.SLOW_LOG);
        put(CompositeCommand.create(Command.SMISMEMBER), ResultParserFactory.BOOLEAN);
        put(CompositeCommand.create(Command.SMOVE), ResultParserFactory.BOOLEAN);
        // TODO: ? put(RedisCompositeCommand.create(Command.SORT), ResultParserFactory.?);
        // TODO: ? put(RedisCompositeCommand.create(Command.SORT_RO), ResultParserFactory.?);
        put(CompositeCommand.create(Command.SREM), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.SSCAN), ResultParserFactory.STRING_SCAN_RESULT);
        put(CompositeCommand.create(Command.STRLEN), ResultParserFactory.LONG);
        // TODO: ? put(RedisCompositeCommand.create(Command.SUBSCRIBE), ResultParserFactory.?);
        put(CompositeCommand.create(Command.SUNIONSTORE), ResultParserFactory.LONG);
        // TODO: ? put(RedisCompositeCommand.create(Command.TIME), ResultParserFactory.?); - 2 elements
        put(CompositeCommand.create(Command.SUNIONSTORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.TOUCH), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.TTL), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.UNLINK), ResultParserFactory.LONG);
        // TODO: ? put(RedisCompositeCommand.create(Command.UNSUBSCRIBE), ResultParserFactory.?);
        put(CompositeCommand.create(Command.WAIT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.XACK), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.XADD), ResultParserFactory.STREAM_ENTRY_ID);
        put(CompositeCommand.create(Command.XCLAIM), ResultParserFactory.STREAM_ENTRY);
        put(CompositeCommand.create(Command.XDEL), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.XGROUP, Keyword.DELCONSUMER), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.XGROUP, Keyword.DESTROY), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.XINFO, Keyword.CONSUMERS), ResultParserFactory.STREAM_CONSUMERS_INFO);
        put(CompositeCommand.create(Command.XINFO, Keyword.GROUPS), ResultParserFactory.STREAM_GROUP_INFO);
        put(CompositeCommand.create(Command.XINFO, Keyword.STREAM), ResultParserFactory.STREAM_INFO);
        put(CompositeCommand.create(Command.XLEN), ResultParserFactory.LONG);
        // TODO: put(RedisCompositeCommand.create(Command.XPENDING), ResultParserFactory.?);
        put(CompositeCommand.create(Command.XRANGE), ResultParserFactory.STREAM_ENTRY);
        put(CompositeCommand.create(Command.XREAD), ResultParserFactory.STREAM_READ);
        put(CompositeCommand.create(Command.XREADGROUP), ResultParserFactory.STREAM_READ);
        put(CompositeCommand.create(Command.XREVRANGE), ResultParserFactory.STREAM_ENTRY);
        put(CompositeCommand.create(Command.XTRIM), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZADD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZCARD), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZCOUNT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZDIFF, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZDIFFSTORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZINCRBY), ResultParserFactory.DOUBLE);
        put(CompositeCommand.create(Command.ZINTER, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZINTERSTORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZLEXCOUNT), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZMSCORE), ResultParserFactory.DOUBLE);
        put(CompositeCommand.create(Command.ZPOPMAX), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZPOPMIN), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZRANDMEMBER, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZRANGE, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZRANGEBYSCORE, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZRANK), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZREM), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZREMRANGEBYRANK), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZREMRANGEBYSCORE), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZREVRANGE, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZREVRANGEBYSCORE, Keyword.WITHSCORES), ResultParserFactory.TUPLE);
        put(CompositeCommand.create(Command.ZREVRANK), ResultParserFactory.LONG);
        put(CompositeCommand.create(Command.ZSCAN), ResultParserFactory.TUPLE_SCAN_RESULT);
        put(CompositeCommand.create(Command.ZSCORE), ResultParserFactory.DOUBLE);
    }};

    private static @NotNull ResultParser getResultParser(@NotNull CompositeCommand command) {
        return RESULT_PARSERS.getOrDefault(command, ResultParserFactory.RESULT);
    }

    public static @NotNull RedisResult<?, ?> parseResult(@NotNull RedisQuery query, @Nullable Object data) {
        return getResultParser(query.getCompositeCommand()).parse(query, data);
    }
}
