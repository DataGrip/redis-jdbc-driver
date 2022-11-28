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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static jdbc.Utils.param;
import static jdbc.Utils.paramCount;
import static jdbc.client.helpers.result.parser.ResultParserFactory.*;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrap;
import static jdbc.client.helpers.result.parser.ResultParserWrapper.wrapList;
import static jdbc.client.structures.query.CompositeCommand.create;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    private static final Map<CompositeCommand, List<ResultParserWrapper>> RESULT_PARSERS = new HashMap<>() {{
        put(create(Command.ACL, Keyword.DELUSER), wrapList(LONG));
        put(create(Command.ACL, Keyword.GETUSER), wrapList(ACCESS_CONTROL_USER));
        put(create(Command.ACL, Keyword.LOG), wrapList(ACCESS_CONTROL_LOG_ENTRY));
        put(create(Command.APPEND), wrapList(LONG));
        put(create(Command.BITCOUNT), wrapList(LONG));
        put(create(Command.BITFIELD), wrapList(LONG));
        put(create(Command.BITFIELD_RO), wrapList(LONG));
        put(create(Command.BITOP), wrapList(LONG));
        put(create(Command.BITPOS), wrapList(LONG));
        put(create(Command.BLMPOP), wrapList(KEYED_STRING_LIST));
        put(create(Command.BZMPOP), wrapList(KEYED_TUPLE_LIST));
        put(create(Command.BZPOPMAX), wrapList(KEYED_TUPLE));
        put(create(Command.BZPOPMIN), wrapList(KEYED_TUPLE));
        put(create(Command.CLIENT, Keyword.ID), wrapList(LONG));
        put(create(Command.CLIENT, Keyword.UNBLOCK), wrapList(LONG));
        put(create(Command.COMMAND, Keyword.COUNT), wrapList(LONG));
        put(create(Command.COMMAND, Keyword.DOCS), wrapList(COMMAND_DOCUMENT));
        put(create(Command.COMMAND, Keyword.GETKEYSANDFLAGS), wrapList(KEYED_STRING_LIST));
        put(create(Command.COMMAND, Keyword.INFO), wrapList(COMMAND_INFO));
        put(create(Command.CONFIG, Keyword.GET), wrapList(STRING_MAP));
        put(create(Command.COPY), wrapList(BOOLEAN));
        put(create(Command.DBSIZE), wrapList(LONG));
        put(create(Command.DECR), wrapList(LONG));
        put(create(Command.DECRBY), wrapList(LONG));
        put(create(Command.DEL), wrapList(LONG));
        put(create(Command.DUMP), wrapList(BYTE_ARRAY));
        put(create(Command.EXISTS), wrapList(LONG));
        put(create(Command.EXPIRE), wrapList(LONG));
        put(create(Command.EXPIREAT), wrapList(LONG));
        put(create(Command.FUNCTION, Keyword.LIST), wrapList(LIBRARY_INFO));
        put(create(Command.FUNCTION, Keyword.STATS), wrapList(FUNCTION_STATS));
        put(create(Command.GEOADD), wrapList(LONG));
        put(create(Command.GEODIST), wrapList(DOUBLE));
        put(create(Command.GEOPOS), wrapList(GEO_COORDINATE));
        put(create(Command.GEORADIUS), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUS_RO), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUSBYMEMBER), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GEORADIUSBYMEMBER_RO), wrapList(GEORADIUS_RESPONSE));
        put(create(Command.GETBIT), wrapList(LONG));
        put(create(Command.HDEL), wrapList(LONG));
        put(create(Command.HEXISTS), wrapList(BOOLEAN));
        put(create(Command.HGETALL), wrapList(STRING_MAP));
        put(create(Command.HINCRBY), wrapList(LONG));
        put(create(Command.HINCRBYFLOAT), wrapList(DOUBLE));
        put(create(Command.HLEN), wrapList(LONG));
        put(create(Command.HRANDFIELD), wrapList(STRING_MAP, param(Keyword.WITHVALUES)));
        put(create(Command.HSCAN), wrapList(ENTRY_SCAN_RESULT));
        put(create(Command.HSET), wrapList(LONG));
        put(create(Command.HSETNX), wrapList(BOOLEAN));
        put(create(Command.HSTRLEN), wrapList(LONG));
        put(create(Command.INCR), wrapList(LONG));
        put(create(Command.INCRBY), wrapList(LONG));
        put(create(Command.INCRBYFLOAT), wrapList(DOUBLE));
        put(create(Command.LASTSAVE), wrapList(LONG));
        // TODO: put(create(Command.LCS), wrapList(???)) - LCSMatchResult (MatchedPosition, Position)
        put(create(Command.LINSERT), wrapList(LONG));
        put(create(Command.LLEN), wrapList(LONG));
        put(create(Command.LMPOP), wrapList(KEYED_STRING_LIST));
        put(create(Command.LPOS), wrapList(LONG));
        put(create(Command.LPUSH), wrapList(LONG));
        put(create(Command.LPUSHX), wrapList(LONG));
        put(create(Command.LREM), wrapList(LONG));
        put(create(Command.MEMORY, Keyword.STATS), wrapList(OBJECT_MAP));
        put(create(Command.MEMORY, Keyword.USAGE), wrapList(LONG));
        put(create(Command.MODULE, Keyword.LIST), wrapList(MODULE));
        put(create(Command.MOVE), wrapList(BOOLEAN));
        put(create(Command.MSETNX), wrapList(BOOLEAN));
        put(create(Command.OBJECT, Keyword.FREQ), wrapList(LONG));
        put(create(Command.OBJECT, Keyword.IDLETIME), wrapList(LONG));
        put(create(Command.OBJECT, Keyword.REFCOUNT), wrapList(LONG));
        put(create(Command.PERSIST), wrapList(BOOLEAN));
        put(create(Command.PEXPIRE), wrapList(BOOLEAN));
        put(create(Command.PEXPIREAT), wrapList(BOOLEAN));
        put(create(Command.PFADD), wrapList(BOOLEAN));
        put(create(Command.PFCOUNT), wrapList(LONG));
        put(create(Command.PTTL), wrapList(LONG));
        put(create(Command.PUBLISH), wrapList(LONG));
        put(create(Command.RENAMENX), wrapList(BOOLEAN));
        put(create(Command.RPUSH), wrapList(LONG));
        put(create(Command.RPUSHX), wrapList(LONG));
        put(create(Command.SADD), wrapList(LONG));
        put(create(Command.SCAN), wrapList(STRING_SCAN_RESULT));
        put(create(Command.SCARD), wrapList(LONG));
        put(create(Command.SCRIPT, Keyword.EXISTS), wrapList(BOOLEAN));
        put(create(Command.SDIFFSTORE), wrapList(LONG));
        put(create(Command.SETBIT), wrapList(LONG));
        put(create(Command.SETNX), wrapList(BOOLEAN));
        put(create(Command.SETRANGE), wrapList(LONG));
        put(create(Command.SINTERSTORE), wrapList(LONG));
        put(create(Command.SISMEMBER), wrapList(BOOLEAN));
        put(create(Command.SLOWLOG, Keyword.GET), wrapList(SLOW_LOG));
        put(create(Command.SLOWLOG, Keyword.LEN), wrapList(LONG));
        put(create(Command.SMISMEMBER), wrapList(BOOLEAN));
        put(create(Command.SMOVE), wrapList(BOOLEAN));
        put(create(Command.SORT), wrapList(LONG, param(Keyword.STORE)));
        put(create(Command.SREM), wrapList(LONG));
        put(create(Command.SSCAN), wrapList(STRING_SCAN_RESULT));
        put(create(Command.STRLEN), wrapList(LONG));
        put(create(Command.SUNIONSTORE), wrapList(LONG));
        put(create(Command.SUNIONSTORE), wrapList(LONG));
        put(create(Command.TOUCH), wrapList(LONG));
        put(create(Command.TTL), wrapList(LONG));
        put(create(Command.UNLINK), wrapList(LONG));
        put(create(Command.WAIT), wrapList(LONG));
        put(create(Command.XACK), wrapList(LONG));
        put(create(Command.XADD), wrapList(STREAM_ENTRY_ID));
        put(create(Command.XCLAIM), wrapList(STREAM_ENTRY));
        put(create(Command.XDEL), wrapList(LONG));
        put(create(Command.XGROUP, Keyword.DELCONSUMER), wrapList(LONG));
        put(create(Command.XGROUP, Keyword.DESTROY), wrapList(LONG));
        put(create(Command.XINFO, Keyword.CONSUMERS), wrapList(STREAM_CONSUMER_INFO));
        put(create(Command.XINFO, Keyword.GROUPS), wrapList(STREAM_GROUP_INFO));
        put(create(Command.XINFO, Keyword.STREAM), asList(wrap(STREAM_INFO_FULL, param(Keyword.FULL)), wrap(STREAM_INFO)));
        put(create(Command.XLEN), wrapList(LONG));
        put(create(Command.XPENDING), asList(wrap(STREAM_PENDING_SUMMARY, paramCount(2)), wrap(STREAM_PENDING_ENTRY)));
        put(create(Command.XRANGE), wrapList(STREAM_ENTRY));
        put(create(Command.XREAD), wrapList(STREAM_READ));
        put(create(Command.XREADGROUP), wrapList(STREAM_READ));
        put(create(Command.XREVRANGE), wrapList(STREAM_ENTRY));
        put(create(Command.XTRIM), wrapList(LONG));
        put(create(Command.ZADD), wrapList(LONG));
        put(create(Command.ZCARD), wrapList(LONG));
        put(create(Command.ZCOUNT), wrapList(LONG));
        put(create(Command.ZDIFF), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZDIFFSTORE), wrapList(LONG));
        put(create(Command.ZINCRBY), wrapList(DOUBLE));
        put(create(Command.ZINTER), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZINTERSTORE), wrapList(LONG));
        put(create(Command.ZLEXCOUNT), wrapList(LONG));
        put(create(Command.ZMPOP), wrapList(KEYED_TUPLE_LIST));
        put(create(Command.ZMSCORE), wrapList(DOUBLE));
        put(create(Command.ZPOPMAX), wrapList(TUPLE));
        put(create(Command.ZPOPMIN), wrapList(TUPLE));
        put(create(Command.ZRANDMEMBER), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZRANGE), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZRANGEBYSCORE), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZRANK), wrapList(LONG));
        put(create(Command.ZREM), wrapList(LONG));
        put(create(Command.ZREMRANGEBYRANK), wrapList(LONG));
        put(create(Command.ZREMRANGEBYSCORE), wrapList(LONG));
        put(create(Command.ZREVRANGE), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZREVRANGEBYSCORE), wrapList(TUPLE, param(Keyword.WITHSCORES)));
        put(create(Command.ZREVRANK), wrapList(LONG));
        put(create(Command.ZSCAN), wrapList(TUPLE_SCAN_RESULT));
        put(create(Command.ZSCORE), wrapList(DOUBLE));
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
