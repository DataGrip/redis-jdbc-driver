package jdbc.client.helpers;

import jdbc.client.extensions.BuilderFactoryEx;
import jdbc.client.helpers.RedisQuery.CompositeCommand;
import redis.clients.jedis.Builder;
import redis.clients.jedis.BuilderFactory;
import redis.clients.jedis.Protocol;

import java.util.HashMap;
import java.util.Map;

public class RedisResultEncoder {

    private RedisResultEncoder() {
    }

    private static final Map<CompositeCommand, Builder<?>> RESULT_BUILDERS = new HashMap<>() {{
        put(CompositeCommand.create(Protocol.Command.ACL, Protocol.Keyword.DELUSER), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ACL, Protocol.Keyword.GETUSER), BuilderFactory.ACCESS_CONTROL_USER);
        put(CompositeCommand.create(Protocol.Command.ACL, Protocol.Keyword.LOG), BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST);
        put(CompositeCommand.create(Protocol.Command.APPEND), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.BITCOUNT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.BITFIELD), BuilderFactory.LONG_LIST);
        put(CompositeCommand.create(Protocol.Command.BITFIELD_RO), BuilderFactory.LONG_LIST);
        put(CompositeCommand.create(Protocol.Command.BITFIELD_RO), BuilderFactory.LONG_LIST);
        put(CompositeCommand.create(Protocol.Command.BITOP), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.BITPOS), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.BLPOP), BuilderFactory.KEYED_LIST_ELEMENT);
        put(CompositeCommand.create(Protocol.Command.BRPOP), BuilderFactory.KEYED_LIST_ELEMENT);
        put(CompositeCommand.create(Protocol.Command.BZPOPMAX), BuilderFactory.KEYED_ZSET_ELEMENT);
        put(CompositeCommand.create(Protocol.Command.BZPOPMIN), BuilderFactory.KEYED_ZSET_ELEMENT);
        put(CompositeCommand.create(Protocol.Command.CLIENT, Protocol.Keyword.ID), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.CLIENT, Protocol.Keyword.UNBLOCK), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.CONFIG, Protocol.Keyword.GET), BuilderFactory.STRING_MAP);
        put(CompositeCommand.create(Protocol.Command.COPY), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.DBSIZE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.DECR), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.DECRBY), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.DEL), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.DUMP), BuilderFactory.BYTE_ARRAY); // think about it
        put(CompositeCommand.create(Protocol.Command.EXISTS), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.EXPIRE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.EXPIREAT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.GEOADD), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.GEODIST), BuilderFactory.DOUBLE);
        put(CompositeCommand.create(Protocol.Command.GEOPOS), BuilderFactory.GEO_COORDINATE_LIST);
        put(CompositeCommand.create(Protocol.Command.GEORADIUS), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
        put(CompositeCommand.create(Protocol.Command.GEORADIUS_RO), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
        put(CompositeCommand.create(Protocol.Command.GEORADIUSBYMEMBER), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
        put(CompositeCommand.create(Protocol.Command.GEORADIUSBYMEMBER_RO), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
        put(CompositeCommand.create(Protocol.Command.GETBIT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.HDEL), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.HEXISTS), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.HGETALL), BuilderFactory.STRING_MAP);
        put(CompositeCommand.create(Protocol.Command.HINCRBY), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.HINCRBYFLOAT), BuilderFactory.DOUBLE);
        put(CompositeCommand.create(Protocol.Command.HLEN), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Protocol.Command.HRANDFIELD), ???); - check last param: WITHVALUES
        // TODO: put(CompositeCommand.create(Protocol.Command.HSCAN), ???); - Builder with ScanResult
        put(CompositeCommand.create(Protocol.Command.HSET), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.HSETNX), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.HSTRLEN), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.INCR), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.INCRBY), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.INCRBYFLOAT), BuilderFactory.DOUBLE);
        put(CompositeCommand.create(Protocol.Command.LASTSAVE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.LINSERT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.LLEN), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Protocol.Command.LPOS), BuilderFactory.LONG); - depends on count argument
        put(CompositeCommand.create(Protocol.Command.LPUSH), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.LPUSHX), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.LREM), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Protocol.Command.Memory, Protocol.Keyword.STATS), BuilderFactory.STRING_MAP); - need KeywordEx
        put(CompositeCommand.create(Protocol.Command.MEMORY, Protocol.Keyword.USAGE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.MODULE, Protocol.Keyword.LIST), BuilderFactory.MODULE_LIST);
        put(CompositeCommand.create(Protocol.Command.MOVE), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.MSETNX), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.OBJECT, Protocol.Keyword.FREQ), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.OBJECT, Protocol.Keyword.IDLETIME), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.OBJECT, Protocol.Keyword.REFCOUNT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.PERSIST), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.PEXPIRE), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.PEXPIREAT), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.PFADD), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.PFCOUNT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.PTTL), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.PUBLISH), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.RENAMENX), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.RPUSH), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.RPUSHX), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SADD), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SCAN), BuilderFactoryEx.STRING_SCAN_RESULT);
        put(CompositeCommand.create(Protocol.Command.SCARD), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SCRIPT, Protocol.Keyword.EXISTS), BuilderFactory.BOOLEAN_LIST);
        put(CompositeCommand.create(Protocol.Command.SDIFFSTORE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SETBIT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SETNX), BuilderFactory.BOOLEAN);
        put(CompositeCommand.create(Protocol.Command.SETRANGE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SINTERSTORE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SISMEMBER), BuilderFactory.BOOLEAN);
        // TODO: put(CompositeCommand.create(Command.SLOWLOG, Keyword.GET), BuilderFactory.SLOW_LOG_LIST);
        put(CompositeCommand.create(Protocol.Command.SMISMEMBER), BuilderFactory.BOOLEAN_LIST);
        put(CompositeCommand.create(Protocol.Command.SMOVE), BuilderFactory.BOOLEAN);
        // TODO: ? put(CompositeCommand.create(Command.SORT), BuilderFactory.?);
        // TODO: ? put(CompositeCommand.create(Command.SORT_RO), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.SREM), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.SSCAN), BuilderFactoryEx.STRING_SCAN_RESULT);
        put(CompositeCommand.create(Protocol.Command.STRLEN), BuilderFactory.LONG);
        // TODO: ? put(CompositeCommand.create(Command.SUBSCRIBE), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.SUNIONSTORE), BuilderFactory.LONG);
        // TODO: ? put(CompositeCommand.create(Command.TIME), BuilderFactory.?); - 2 elements
        put(CompositeCommand.create(Protocol.Command.SUNIONSTORE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.TOUCH), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.TTL), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.UNLINK), BuilderFactory.LONG);
        // TODO: ? put(CompositeCommand.create(Command.UNSUBSCRIBE), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.WAIT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.XACK), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.XADD), BuilderFactory.STREAM_ENTRY_ID);
        put(CompositeCommand.create(Protocol.Command.XCLAIM), BuilderFactory.STREAM_ENTRY_LIST);
        put(CompositeCommand.create(Protocol.Command.XDEL), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.XGROUP, Protocol.Keyword.DELCONSUMER), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.XGROUP, Protocol.Keyword.DESTROY), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.XINFO, Protocol.Keyword.CONSUMERS), BuilderFactory.STREAM_CONSUMERS_INFO_LIST);
        put(CompositeCommand.create(Protocol.Command.XINFO, Protocol.Keyword.GROUPS), BuilderFactory.STREAM_GROUP_INFO_LIST);
        put(CompositeCommand.create(Protocol.Command.XINFO, Protocol.Keyword.STREAM), BuilderFactory.STREAM_INFO);
        put(CompositeCommand.create(Protocol.Command.XLEN), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Command.XPENDING), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.XRANGE), BuilderFactory.STREAM_ENTRY_LIST);
        put(CompositeCommand.create(Protocol.Command.XREAD), BuilderFactory.STREAM_READ_RESPONSE);
        put(CompositeCommand.create(Protocol.Command.XREADGROUP), BuilderFactory.STREAM_READ_RESPONSE); // TODO: think about block param
        put(CompositeCommand.create(Protocol.Command.XREVRANGE), BuilderFactory.STREAM_ENTRY_LIST);
        put(CompositeCommand.create(Protocol.Command.XTRIM), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZADD), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZCARD), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZCOUNT), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Command.ZDIFF), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.ZDIFFSTORE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZINCRBY), BuilderFactory.DOUBLE);
        // TODO: put(CompositeCommand.create(Command.ZINTER), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.ZINTERSTORE), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZLEXCOUNT), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZMSCORE), BuilderFactory.DOUBLE_LIST);
        // TODO: put(CompositeCommand.create(Command.ZPOPMAX), BuilderFactory.?);
        // TODO: put(CompositeCommand.create(Command.ZPOPMIN), BuilderFactory.?);
        // TODO: put(CompositeCommand.create(Command.ZRANDMEMBER), BuilderFactory.?);
        // TODO: put(CompositeCommand.create(Command.ZRANGE), BuilderFactory.?);
        // TODO: put(CompositeCommand.create(Command.ZRANGEBYSCORE), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.ZRANK), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZREM), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZREMRANGEBYRANK), BuilderFactory.LONG);
        put(CompositeCommand.create(Protocol.Command.ZREMRANGEBYSCORE), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Command.ZREVRANGE), BuilderFactory.?);
        // TODO: put(CompositeCommand.create(Command.ZREVRANGEBTSCORE), BuilderFactory.?);
        put(CompositeCommand.create(Protocol.Command.ZREVRANK), BuilderFactory.LONG);
        // TODO: put(CompositeCommand.create(Command.ZSCAN), BuilderFactory.TUPLE_SCAN_RESULT);
        put(CompositeCommand.create(Protocol.Command.ZSCORE), BuilderFactory.DOUBLE);
    }};

    private static Builder<?> getResultBuilder(CompositeCommand command) {
        return RESULT_BUILDERS.getOrDefault(command, BuilderFactory.ENCODED_OBJECT);
    }

    public static Object encodeResult(RedisQuery query, Object result) {
        if (result == null) return null;
        Builder<?> resultBuilder = getResultBuilder(query.getCompositeCommand());
        return resultBuilder.build(result);
    }
}
