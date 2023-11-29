package jdbc.client.commands;

import jdbc.client.commands.ProtocolExtensions.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.ClusterKeyword;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.bloom.RedisBloomProtocol.*;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;
import redis.clients.jedis.search.SearchProtocol.SearchCommand;
import redis.clients.jedis.search.SearchProtocol.SearchKeyword;
import redis.clients.jedis.timeseries.TimeSeriesProtocol.TimeSeriesCommand;

import static jdbc.utils.Utils.getName;

public class RedisCommands {

    /* --------------------------------------------- Native --------------------------------------------- */

    public static final RedisCommand ACL_CAT                 = create(Command.ACL, Keyword.CAT);
    public static final RedisCommand ACL_DELUSER             = create(Command.ACL, Keyword.DELUSER);
    public static final RedisCommand ACL_DRYRUN              = create(Command.ACL, Keyword.DRYRUN);
    public static final RedisCommand ACL_GENPASS             = create(Command.ACL, Keyword.GENPASS);
    public static final RedisCommand ACL_GETUSER             = create(Command.ACL, Keyword.GETUSER);
    public static final RedisCommand ACL_LIST                = create(Command.ACL, Keyword.LIST);
    public static final RedisCommand ACL_LOAD                = create(Command.ACL, Keyword.LOAD);
    public static final RedisCommand ACL_LOG                 = create(Command.ACL, Keyword.LOG);
    public static final RedisCommand ACL_SAVE                = create(Command.ACL, Keyword.SAVE);
    public static final RedisCommand ACL_SETUSER             = create(Command.ACL, Keyword.SETUSER);
    public static final RedisCommand ACL_USERS               = create(Command.ACL, Keyword.USERS);
    public static final RedisCommand ACL_WHOAMI              = create(Command.ACL, Keyword.WHOAMI);
    public static final RedisCommand APPEND                  = create(Command.APPEND);
    public static final RedisCommand AUTH                    = create(Command.AUTH);
    public static final RedisCommand BGREWRITEAOF            = create(Command.BGREWRITEAOF);
    public static final RedisCommand BGSAVE                  = create(Command.BGSAVE);
    public static final RedisCommand BITCOUNT                = create(Command.BITCOUNT);
    public static final RedisCommand BITFIELD                = create(Command.BITFIELD);
    public static final RedisCommand BITFIELD_RO             = create(Command.BITFIELD_RO);
    public static final RedisCommand BITOP                   = create(Command.BITOP);
    public static final RedisCommand BITPOS                  = create(Command.BITPOS);
    public static final RedisCommand BLMOVE                  = create(Command.BLMOVE);
    public static final RedisCommand BLMPOP                  = create(Command.BLMPOP);
    public static final RedisCommand BLPOP                   = create(Command.BLPOP);
    public static final RedisCommand BRPOP                   = create(Command.BRPOP);
    public static final RedisCommand BRPOPLPUSH              = create(Command.BRPOPLPUSH);
    public static final RedisCommand BZMPOP                  = create(Command.BZMPOP);
    public static final RedisCommand BZPOPMAX                = create(Command.BZPOPMAX);
    public static final RedisCommand BZPOPMIN                = create(Command.BZPOPMIN);
    public static final RedisCommand CLIENT_CACHING         = create(Command.CLIENT, KeywordEx.CACHING);
    public static final RedisCommand CLIENT_GETNAME          = create(Command.CLIENT, Keyword.GETNAME);
    public static final RedisCommand CLIENT_GETREDIR        = create(Command.CLIENT, KeywordEx.GETREDIR);
    public static final RedisCommand CLIENT_ID               = create(Command.CLIENT, Keyword.ID);
    public static final RedisCommand CLIENT_INFO             = create(Command.CLIENT, Keyword.INFO);
    public static final RedisCommand CLIENT_KILL             = create(Command.CLIENT, Keyword.KILL);
    public static final RedisCommand CLIENT_LIST             = create(Command.CLIENT, Keyword.LIST);
    public static final RedisCommand CLIENT_NOEVICT          = create(Command.CLIENT, KeywordEx.NOEVICT);
    public static final RedisCommand CLIENT_NOTOUCH          = create(Command.CLIENT, KeywordEx.NOTOUCH);
    public static final RedisCommand CLIENT_PAUSE            = create(Command.CLIENT, Keyword.PAUSE);
    public static final RedisCommand CLIENT_REPLY            = create(Command.CLIENT, KeywordEx.REPLY);
    public static final RedisCommand CLIENT_SETINFO          = create(Command.CLIENT, Keyword.SETINFO);
    public static final RedisCommand CLIENT_SETNAME          = create(Command.CLIENT, Keyword.SETNAME);
    public static final RedisCommand CLIENT_TRACKING         = create(Command.CLIENT, KeywordEx.TRACKING);
    public static final RedisCommand CLIENT_TRACKINGINFO     = create(Command.CLIENT, KeywordEx.TRACKINGINFO);
    public static final RedisCommand CLIENT_UNBLOCK          = create(Command.CLIENT, Keyword.UNBLOCK);
    public static final RedisCommand CLIENT_UNPAUSE          = create(Command.CLIENT, Keyword.UNPAUSE);
    public static final RedisCommand COMMAND                 = create(Command.COMMAND);
    public static final RedisCommand COMMAND_COUNT           = create(Command.COMMAND, Keyword.COUNT);
    public static final RedisCommand COMMAND_DOCS            = create(Command.COMMAND, Keyword.DOCS);
    public static final RedisCommand COMMAND_GETKEYS         = create(Command.COMMAND, Keyword.GETKEYS);
    public static final RedisCommand COMMAND_GETKEYSANDFLAGS = create(Command.COMMAND, Keyword.GETKEYSANDFLAGS);
    public static final RedisCommand COMMAND_INFO            = create(Command.COMMAND, Keyword.INFO);
    public static final RedisCommand COMMAND_LIST            = create(Command.COMMAND, Keyword.LIST);
    public static final RedisCommand CONFIG_GET              = create(Command.CONFIG, Keyword.GET);
    public static final RedisCommand CONFIG_RESETSTAT        = create(Command.CONFIG, Keyword.RESETSTAT);
    public static final RedisCommand CONFIG_REWRITE          = create(Command.CONFIG, Keyword.REWRITE);
    public static final RedisCommand CONFIG_SET              = create(Command.CONFIG, Keyword.SET);
    public static final RedisCommand COPY                    = create(Command.COPY);
    public static final RedisCommand DBSIZE                  = create(Command.DBSIZE);
    public static final RedisCommand DECR                    = create(Command.DECR);
    public static final RedisCommand DECRBY                  = create(Command.DECRBY);
    public static final RedisCommand DEL                     = create(Command.DEL);
    public static final RedisCommand DISCARD                 = create(Command.DISCARD);
    public static final RedisCommand DUMP                    = create(Command.DUMP);
    public static final RedisCommand ECHO                    = create(Command.ECHO);
    public static final RedisCommand EVAL                    = create(Command.EVAL);
    public static final RedisCommand EVAL_RO                 = create(Command.EVAL_RO);
    public static final RedisCommand EVALSHA                 = create(Command.EVALSHA);
    public static final RedisCommand EVALSHA_RO              = create(Command.EVALSHA_RO);
    public static final RedisCommand EXEC                    = create(Command.EXEC);
    public static final RedisCommand EXISTS                  = create(Command.EXISTS);
    public static final RedisCommand EXPIRE                  = create(Command.EXPIRE);
    public static final RedisCommand EXPIREAT                = create(Command.EXPIREAT);
    public static final RedisCommand EXPIRETIME              = create(Command.EXPIRETIME);
    public static final RedisCommand FCALL                   = create(Command.FCALL);
    public static final RedisCommand FCALL_RO                = create(Command.FCALL_RO);
    public static final RedisCommand FLUSHALL                = create(Command.FLUSHALL);
    public static final RedisCommand FLUSHDB                 = create(Command.FLUSHDB);
    public static final RedisCommand FUNCTION_DELETE         = create(Command.FUNCTION, Keyword.DELETE);
    public static final RedisCommand FUNCTION_DUMP           = create(Command.FUNCTION, Keyword.DUMP);
    public static final RedisCommand FUNCTION_FLUSH          = create(Command.FUNCTION, Keyword.FLUSH);
    public static final RedisCommand FUNCTION_KILL           = create(Command.FUNCTION, Keyword.KILL);
    public static final RedisCommand FUNCTION_LIST           = create(Command.FUNCTION, Keyword.LIST);
    public static final RedisCommand FUNCTION_LOAD           = create(Command.FUNCTION, Keyword.LOAD);
    public static final RedisCommand FUNCTION_RESTORE        = create(Command.FUNCTION, KeywordEx.RESTORE);
    public static final RedisCommand FUNCTION_STATS          = create(Command.FUNCTION, Keyword.STATS);
    public static final RedisCommand GEOADD                  = create(Command.GEOADD);
    public static final RedisCommand GEODIST                 = create(Command.GEODIST);
    public static final RedisCommand GEOHASH                 = create(Command.GEOHASH);
    public static final RedisCommand GEOPOS                  = create(Command.GEOPOS);
    public static final RedisCommand GEORADIUS               = create(Command.GEORADIUS);
    public static final RedisCommand GEORADIUS_RO            = create(Command.GEORADIUS_RO);
    public static final RedisCommand GEORADIUSBYMEMBER       = create(Command.GEORADIUSBYMEMBER);
    public static final RedisCommand GEORADIUSBYMEMBER_RO    = create(Command.GEORADIUSBYMEMBER_RO);
    public static final RedisCommand GEOSEARCH               = create(Command.GEOSEARCH);
    public static final RedisCommand GEOSEARCHSTORE          = create(Command.GEOSEARCHSTORE);
    public static final RedisCommand GET                     = create(Command.GET);
    public static final RedisCommand GETBIT                  = create(Command.GETBIT);
    public static final RedisCommand GETDEL                  = create(Command.GETDEL);
    public static final RedisCommand GETEX                   = create(Command.GETEX);
    public static final RedisCommand GETRANGE                = create(Command.GETRANGE);
    public static final RedisCommand GETSET                  = create(Command.GETSET);
    public static final RedisCommand HDEL                    = create(Command.HDEL);
    public static final RedisCommand HELLO                   = create(CommandEx.HELLO);
    public static final RedisCommand HEXISTS                 = create(Command.HEXISTS);
    public static final RedisCommand HGET                    = create(Command.HGET);
    public static final RedisCommand HGETALL                 = create(Command.HGETALL);
    public static final RedisCommand HINCRBY                 = create(Command.HINCRBY);
    public static final RedisCommand HINCRBYFLOAT            = create(Command.HINCRBYFLOAT);
    public static final RedisCommand HKEYS                   = create(Command.HKEYS);
    public static final RedisCommand HLEN                    = create(Command.HLEN);
    public static final RedisCommand HRANDFIELD              = create(Command.HRANDFIELD);
    public static final RedisCommand HSCAN                   = create(Command.HSCAN);
    public static final RedisCommand HSET                    = create(Command.HSET);
    public static final RedisCommand HSETNX                  = create(Command.HSETNX);
    public static final RedisCommand HSTRLEN                 = create(Command.HSTRLEN);
    public static final RedisCommand HVALS                   = create(Command.HVALS);
    public static final RedisCommand INCR                    = create(Command.INCR);
    public static final RedisCommand INCRBY                  = create(Command.INCRBY);
    public static final RedisCommand INCRBYFLOAT             = create(Command.INCRBYFLOAT);
    public static final RedisCommand INFO                    = create(Command.INFO);
    public static final RedisCommand KEYS                    = create(Command.KEYS);
    public static final RedisCommand LASTSAVE                = create(Command.LASTSAVE);
    public static final RedisCommand LATENCY_DOCTOR          = create(Command.LATENCY, Keyword.DOCTOR);
    public static final RedisCommand LATENCY_GRAPH           = create(Command.LATENCY, KeywordEx.GRAPH);
    public static final RedisCommand LATENCY_HISTOGRAM       = create(Command.LATENCY, KeywordEx.HISTOGRAM); // TODO: implement result parser
    public static final RedisCommand LATENCY_HISTORY         = create(Command.LATENCY, KeywordEx.HISTORY); // TODO: implement result parser
    public static final RedisCommand LATENCY_LATEST          = create(Command.LATENCY, KeywordEx.LATEST); // TODO: implement result parser
    public static final RedisCommand LATENCY_RESET           = create(Command.LATENCY, Keyword.RESET);
    public static final RedisCommand LCS                     = create(Command.LCS); // TODO: implement result parser
    public static final RedisCommand LINDEX                  = create(Command.LINDEX);
    public static final RedisCommand LINSERT                 = create(Command.LINSERT);
    public static final RedisCommand LLEN                    = create(Command.LLEN);
    public static final RedisCommand LMOVE                   = create(Command.LMOVE);
    public static final RedisCommand LMPOP                   = create(Command.LMPOP);
    public static final RedisCommand LOLWUT                  = create(Command.LOLWUT);
    public static final RedisCommand LPOP                    = create(Command.LPOP);
    public static final RedisCommand LPOS                    = create(Command.LPOS);
    public static final RedisCommand LPUSH                   = create(Command.LPUSH);
    public static final RedisCommand LPUSHX                  = create(Command.LPUSHX);
    public static final RedisCommand LRANGE                  = create(Command.LRANGE);
    public static final RedisCommand LREM                    = create(Command.LREM);
    public static final RedisCommand LSET                    = create(Command.LSET);
    public static final RedisCommand LTRIM                   = create(Command.LTRIM);
    public static final RedisCommand MEMORY_DOCTOR           = create(Command.MEMORY, Keyword.DOCTOR);
    public static final RedisCommand MEMORY_MALLOCSTATS      = create(Command.MEMORY, KeywordEx.MALLOCSTATS);
    public static final RedisCommand MEMORY_PURGE            = create(Command.MEMORY, Keyword.PURGE);
    public static final RedisCommand MEMORY_STATS            = create(Command.MEMORY, Keyword.STATS);
    public static final RedisCommand MEMORY_USAGE            = create(Command.MEMORY, Keyword.USAGE);
    public static final RedisCommand MGET                    = create(Command.MGET);
    public static final RedisCommand MIGRATE                 = create(Command.MIGRATE);
    public static final RedisCommand MODULE_LIST             = create(Command.MODULE, Keyword.LIST);
    public static final RedisCommand MODULE_LOAD             = create(Command.MODULE, Keyword.LOAD);
    public static final RedisCommand MODULE_LOADEX           = create(Command.MODULE, Keyword.LOADEX);
    public static final RedisCommand MODULE_UNLOAD           = create(Command.MODULE, Keyword.UNLOAD);
    public static final RedisCommand MONITOR                 = create(Command.MONITOR);
    public static final RedisCommand MOVE                    = create(Command.MOVE);
    public static final RedisCommand MSET                    = create(Command.MSET);
    public static final RedisCommand MSETNX                  = create(Command.MSETNX);
    public static final RedisCommand MULTI                   = create(Command.MULTI);
    public static final RedisCommand OBJECT_ENCODING         = create(Command.OBJECT, Keyword.ENCODING);
    public static final RedisCommand OBJECT_FREQ             = create(Command.OBJECT, Keyword.FREQ);
    public static final RedisCommand OBJECT_IDLETIME         = create(Command.OBJECT, Keyword.IDLETIME);
    public static final RedisCommand OBJECT_REFCOUNT         = create(Command.OBJECT, Keyword.REFCOUNT);
    public static final RedisCommand PERSIST                 = create(Command.PERSIST);
    public static final RedisCommand PEXPIRE                 = create(Command.PEXPIRE);
    public static final RedisCommand PEXPIREAT               = create(Command.PEXPIREAT);
    public static final RedisCommand PEXPIRETIME             = create(Command.PEXPIRETIME);
    public static final RedisCommand PFADD                   = create(Command.PFADD);
    public static final RedisCommand PFCOUNT                 = create(Command.PFCOUNT);
    // public static final RedisCommand PFDEBUG                 = create(Command.PFDEBUG); - Command.PFDEBUG doesn't exist [INTERNAL]
    public static final RedisCommand PFMERGE                 = create(Command.PFMERGE);
    // public static final RedisCommand PFSELFTEST              = create(Command.PFSELFTEST); - Command.PFSELFTEST doesn't exist [INTERNAL]
    public static final RedisCommand PING                    = create(Command.PING);
    public static final RedisCommand PSETEX                  = create(Command.PSETEX);
    // public static final RedisCommand PSYNC                   = create(Command.PSYNC); - Command.PSYNC doesn't exist [???]
    public static final RedisCommand PTTL                    = create(Command.PTTL);
    public static final RedisCommand PUBLISH                 = create(Command.PUBLISH);
    public static final RedisCommand PUBSUB_CHANNELS         = create(Command.PUBSUB, Keyword.CHANNELS);
    public static final RedisCommand PUBSUB_NUMPAT           = create(Command.PUBSUB, Keyword.NUMPAT);
    public static final RedisCommand PUBSUB_NUMSUB           = create(Command.PUBSUB, Keyword.NUMSUB); // TODO: implement result parser
    public static final RedisCommand PUBSUB_SHARDCHANNELS    = create(Command.PUBSUB, KeywordEx.SHARDCHANNELS);
    public static final RedisCommand PUBSUB_SHARDNUMSUB      = create(Command.PUBSUB, KeywordEx.SHARDNUMSUB); // TODO: result parser
    public static final RedisCommand QUIT                    = create(Command.QUIT);
    public static final RedisCommand RANDOMKEY               = create(Command.RANDOMKEY);
    public static final RedisCommand READONLY                = create(Command.READONLY);
    public static final RedisCommand READWRITE               = create(Command.READWRITE);
    public static final RedisCommand RENAME                  = create(Command.RENAME);
    public static final RedisCommand RENAMENX                = create(Command.RENAMENX);
    public static final RedisCommand REPLCONF                = create(CommandEx.REPLCONF);
    public static final RedisCommand REPLICAOF               = create(Command.REPLICAOF);
    public static final RedisCommand RESET                   = create(CommandEx.RESET);
    public static final RedisCommand RESTORE                 = create(Command.RESTORE);
    public static final RedisCommand RESTOREASKING           = create(CommandEx.RESTOREASKING);
    public static final RedisCommand ROLE                    = create(Command.ROLE);
    public static final RedisCommand RPOP                    = create(Command.RPOP);
    public static final RedisCommand RPOPLPUSH               = create(Command.RPOPLPUSH);
    public static final RedisCommand RPUSH                   = create(Command.RPUSH);
    public static final RedisCommand RPUSHX                  = create(Command.RPUSHX);
    public static final RedisCommand SADD                    = create(Command.SADD);
    public static final RedisCommand SAVE                    = create(Command.SAVE);
    public static final RedisCommand SCAN                    = create(Command.SCAN);
    public static final RedisCommand SCARD                   = create(Command.SCARD);
    // public static final RedisCommand SCRIPT_DEBUG            = create(Command.SCRIPT, Keyword.DEBUG); - Keyword.DEBUG doesn't exist [???]
    public static final RedisCommand SCRIPT_EXISTS           = create(Command.SCRIPT, Keyword.EXISTS);
    public static final RedisCommand SCRIPT_FLUSH            = create(Command.SCRIPT, Keyword.FLUSH);
    public static final RedisCommand SCRIPT_KILL             = create(Command.SCRIPT, Keyword.KILL);
    public static final RedisCommand SCRIPT_LOAD             = create(Command.SCRIPT, Keyword.LOAD);
    public static final RedisCommand SDIFF                   = create(Command.SDIFF);
    public static final RedisCommand SDIFFSTORE              = create(Command.SDIFFSTORE);
    public static final RedisCommand SELECT                  = create(Command.SELECT);
    public static final RedisCommand SET                     = create(Command.SET);
    public static final RedisCommand SETBIT                  = create(Command.SETBIT);
    public static final RedisCommand SETEX                   = create(Command.SETEX);
    public static final RedisCommand SETNX                   = create(Command.SETNX);
    public static final RedisCommand SETRANGE                = create(Command.SETRANGE);
    public static final RedisCommand SHUTDOWN                = create(Command.SHUTDOWN);
    public static final RedisCommand SINTER                  = create(Command.SINTER);
    public static final RedisCommand SINTERCARD              = create(Command.SINTERCARD);
    public static final RedisCommand SINTERSTORE             = create(Command.SINTERSTORE);
    public static final RedisCommand SISMEMBER               = create(Command.SISMEMBER);
    public static final RedisCommand SLAVEOF                 = create(Command.SLAVEOF);
    public static final RedisCommand SLOWLOG_GET             = create(Command.SLOWLOG, Keyword.GET);
    public static final RedisCommand SLOWLOG_LEN             = create(Command.SLOWLOG, Keyword.LEN);
    public static final RedisCommand SLOWLOG_RESET           = create(Command.SLOWLOG, Keyword.RESET);
    public static final RedisCommand SMEMBERS                = create(Command.SMEMBERS);
    public static final RedisCommand SMISMEMBER              = create(Command.SMISMEMBER);
    public static final RedisCommand SMOVE                   = create(Command.SMOVE);
    public static final RedisCommand SORT                    = create(Command.SORT);
    public static final RedisCommand SORT_RO                 = create(Command.SORT_RO);
    public static final RedisCommand SPOP                    = create(Command.SPOP);
    public static final RedisCommand SRANDMEMBER             = create(Command.SRANDMEMBER);
    public static final RedisCommand SREM                    = create(Command.SREM);
    public static final RedisCommand SSCAN                   = create(Command.SSCAN);
    public static final RedisCommand STRLEN                  = create(Command.STRLEN);
    public static final RedisCommand SUBSTR                  = create(Command.SUBSTR);
    public static final RedisCommand SUNION                  = create(Command.SUNION);
    public static final RedisCommand SUNIONSTORE             = create(Command.SUNIONSTORE);
    public static final RedisCommand SWAPDB                  = create(Command.SWAPDB);
    // public static final RedisCommand SYNC                    = create(Command.SYNC); - Command.SYNC doesn't exist [???]
    public static final RedisCommand TIME                    = create(Command.TIME);
    public static final RedisCommand TOUCH                   = create(Command.TOUCH);
    public static final RedisCommand TTL                     = create(Command.TTL);
    public static final RedisCommand TYPE                    = create(Command.TYPE);
    public static final RedisCommand UNLINK                  = create(Command.UNLINK);
    public static final RedisCommand UNWATCH                 = create(Command.UNWATCH);
    public static final RedisCommand WAIT                    = create(Command.WAIT);
    public static final RedisCommand WAITAOF                 = create(Command.WAITAOF); // TODO: implement result parser
    public static final RedisCommand WATCH                   = create(Command.WATCH);
    public static final RedisCommand XACK                    = create(Command.XACK);
    public static final RedisCommand XADD                    = create(Command.XADD);
    public static final RedisCommand XAUTOCLAIM              = create(Command.XAUTOCLAIM); // TODO: implement result parser
    public static final RedisCommand XCLAIM                  = create(Command.XCLAIM);
    public static final RedisCommand XDEL                    = create(Command.XDEL);
    public static final RedisCommand XGROUP_CREATE           = create(Command.XGROUP, Keyword.CREATE);
    public static final RedisCommand XGROUP_CREATECONSUMER   = create(Command.XGROUP, Keyword.CREATECONSUMER);
    public static final RedisCommand XGROUP_DELCONSUMER      = create(Command.XGROUP, Keyword.DELCONSUMER);
    public static final RedisCommand XGROUP_DESTROY          = create(Command.XGROUP, Keyword.DESTROY);
    public static final RedisCommand XGROUP_SETID            = create(Command.XGROUP, Keyword.SETID);
    public static final RedisCommand XINFO_CONSUMERS         = create(Command.XINFO, Keyword.CONSUMERS);
    public static final RedisCommand XINFO_GROUPS            = create(Command.XINFO, Keyword.GROUPS);
    public static final RedisCommand XINFO_STREAM            = create(Command.XINFO, Keyword.STREAM);
    public static final RedisCommand XLEN                    = create(Command.XLEN);
    public static final RedisCommand XPENDING                = create(Command.XPENDING);
    public static final RedisCommand XRANGE                  = create(Command.XRANGE);
    public static final RedisCommand XREAD                   = create(Command.XREAD);
    public static final RedisCommand XREADGROUP              = create(Command.XREADGROUP);
    public static final RedisCommand XREVRANGE               = create(Command.XREVRANGE);
    // public static final RedisCommand XSETID                  = create(Command.XSETID); - Command.XSETID doesn't exist [INTERNAL]
    public static final RedisCommand XTRIM                   = create(Command.XTRIM);
    public static final RedisCommand ZADD                    = create(Command.ZADD);
    public static final RedisCommand ZCARD                   = create(Command.ZCARD);
    public static final RedisCommand ZCOUNT                  = create(Command.ZCOUNT);
    public static final RedisCommand ZDIFF                   = create(Command.ZDIFF);
    public static final RedisCommand ZDIFFSTORE              = create(Command.ZDIFFSTORE);
    public static final RedisCommand ZINCRBY                 = create(Command.ZINCRBY);
    public static final RedisCommand ZINTER                  = create(Command.ZINTER);
    public static final RedisCommand ZINTERCARD              = create(Command.ZINTERCARD);
    public static final RedisCommand ZINTERSTORE             = create(Command.ZINTERSTORE);
    public static final RedisCommand ZLEXCOUNT               = create(Command.ZLEXCOUNT);
    public static final RedisCommand ZMPOP                   = create(Command.ZMPOP);
    public static final RedisCommand ZMSCORE                 = create(Command.ZMSCORE);
    public static final RedisCommand ZPOPMAX                 = create(Command.ZPOPMAX);
    public static final RedisCommand ZPOPMIN                 = create(Command.ZPOPMIN);
    public static final RedisCommand ZRANDMEMBER             = create(Command.ZRANDMEMBER);
    public static final RedisCommand ZRANGE                  = create(Command.ZRANGE);
    public static final RedisCommand ZRANGEBYLEX             = create(Command.ZRANGEBYLEX);
    public static final RedisCommand ZRANGEBYSCORE           = create(Command.ZRANGEBYSCORE);
    public static final RedisCommand ZRANK                   = create(Command.ZRANK);
    public static final RedisCommand ZREM                    = create(Command.ZREM);
    public static final RedisCommand ZREMRANGEBYLEX          = create(Command.ZREMRANGEBYLEX);
    public static final RedisCommand ZREMRANGEBYRANK         = create(Command.ZREMRANGEBYRANK);
    public static final RedisCommand ZREMRANGEBYSCORE        = create(Command.ZREMRANGEBYSCORE);
    public static final RedisCommand ZREVRANGE               = create(Command.ZREVRANGE);
    public static final RedisCommand ZREVRANGEBYLEX          = create(Command.ZREVRANGEBYLEX);
    public static final RedisCommand ZREVRANGEBYSCORE        = create(Command.ZREVRANGEBYSCORE);
    public static final RedisCommand ZREVRANK                = create(Command.ZREVRANK);
    public static final RedisCommand ZSCAN                   = create(Command.ZSCAN);
    public static final RedisCommand ZSCORE                  = create(Command.ZSCORE);
    public static final RedisCommand ZUNION                  = create(Command.ZUNION);
    public static final RedisCommand ZUNIONSTORE             = create(Command.ZUNIONSTORE);

    public static final RedisCommand CLUSTER_ADDSLOTS        = create(Command.CLUSTER, ClusterKeyword.ADDSLOTS);
    public static final RedisCommand CLUSTER_ADDSLOTSRANGE   = create(Command.CLUSTER, ClusterKeyword.ADDSLOTSRANGE);
    public static final RedisCommand CLUSTER_BUMPEPOCH       = create(Command.CLUSTER, ClusterKeyword.BUMPEPOCH);
    public static final RedisCommand CLUSTER_COUNTFAILUREREPORT = create(Command.CLUSTER, ClusterKeywordEx.COUNTFAILUREREPORT);
    public static final RedisCommand CLUSTER_COUNTKEYSINSLOT = create(Command.CLUSTER, ClusterKeyword.COUNTKEYSINSLOT);
    public static final RedisCommand CLUSTER_DELSLOTS        = create(Command.CLUSTER, ClusterKeyword.DELSLOTS);
    public static final RedisCommand CLUSTER_DELSLOTSRANGE   = create(Command.CLUSTER, ClusterKeyword.DELSLOTSRANGE);
    public static final RedisCommand CLUSTER_FAILOVER        = create(Command.CLUSTER, ClusterKeyword.FAILOVER);
    public static final RedisCommand CLUSTER_FLUSHSLOTS      = create(Command.CLUSTER, ClusterKeyword.FLUSHSLOTS);
    public static final RedisCommand CLUSTER_FORGET          = create(Command.CLUSTER, ClusterKeyword.FORGET);
    public static final RedisCommand CLUSTER_GETKEYSINSLOT   = create(Command.CLUSTER, ClusterKeyword.GETKEYSINSLOT);
    public static final RedisCommand CLUSTER_INFO            = create(Command.CLUSTER, ClusterKeyword.INFO);
    public static final RedisCommand CLUSTER_KEYSLOT         = create(Command.CLUSTER, ClusterKeyword.KEYSLOT);
    public static final RedisCommand CLUSTER_LINKS           = create(Command.CLUSTER, ClusterKeyword.LINKS); // TODO: result parser
    public static final RedisCommand CLUSTER_MEET            = create(Command.CLUSTER, ClusterKeyword.MEET);
    public static final RedisCommand CLUSTER_MYID            = create(Command.CLUSTER, ClusterKeyword.MYID);
    public static final RedisCommand CLUSTER_MYSHARDID       = create(Command.CLUSTER, ClusterKeyword.MYSHARDID);
    public static final RedisCommand CLUSTER_NODES           = create(Command.CLUSTER, ClusterKeyword.NODES);
    public static final RedisCommand CLUSTER_REPLICAS        = create(Command.CLUSTER, ClusterKeyword.REPLICAS);
    public static final RedisCommand CLUSTER_REPLICATE       = create(Command.CLUSTER, ClusterKeyword.REPLICATE);
    public static final RedisCommand CLUSTER_RESET           = create(Command.CLUSTER, ClusterKeyword.RESET);
    public static final RedisCommand CLUSTER_SAVECONFIG      = create(Command.CLUSTER, ClusterKeyword.SAVECONFIG);  // TODO: result parser
    public static final RedisCommand CLUSTER_SETCONFIGEPOCH  = create(Command.CLUSTER, ClusterKeywordEx.SETCONFIGEPOCH);
    public static final RedisCommand CLUSTER_SETSLOT         = create(Command.CLUSTER, ClusterKeyword.SETSLOT);
    public static final RedisCommand CLUSTER_SHARDS          = create(Command.CLUSTER, ClusterKeywordEx.SHARDS);  // TODO: result parser
    public static final RedisCommand CLUSTER_SLAVES          = create(Command.CLUSTER, ClusterKeyword.SLAVES);
    public static final RedisCommand CLUSTER_SLOTS           = create(Command.CLUSTER, ClusterKeyword.SLOTS);  // TODO: result parser

    /* --------------------------------------------- RedisJSON --------------------------------------------- */

    public static final RedisCommand JSON_ARRAPPEND          = create(JsonCommand.ARRAPPEND);
    public static final RedisCommand JSON_ARRINDEX           = create(JsonCommand.ARRINDEX);
    public static final RedisCommand JSON_ARRINSERT          = create(JsonCommand.ARRINSERT);
    public static final RedisCommand JSON_ARRLEN             = create(JsonCommand.ARRLEN);
    public static final RedisCommand JSON_ARRPOP             = create(JsonCommand.ARRPOP);
    public static final RedisCommand JSON_ARRTRIM            = create(JsonCommand.ARRTRIM);
    public static final RedisCommand JSON_CLEAR              = create(JsonCommand.CLEAR);
    public static final RedisCommand JSON_DEBUG_MEMORY       = create(JsonCommand.DEBUG, JsonKeywordEx.MEMORY);
    public static final RedisCommand JSON_DEL                = create(JsonCommand.DEL);
    public static final RedisCommand JSON_FORGET             = create(JsonCommandEx.FORGET);
    public static final RedisCommand JSON_GET                = create(JsonCommand.GET);
    public static final RedisCommand JSON_MERGE              = create(JsonCommandEx.MERGE);
    public static final RedisCommand JSON_MGET               = create(JsonCommand.MGET);
    public static final RedisCommand JSON_MSET               = create(JsonCommandEx.MSET);
    public static final RedisCommand JSON_NUMINCRBY          = create(JsonCommand.NUMINCRBY);
    public static final RedisCommand JSON_NUMMULTBY          = create(JsonCommandEx.NUMMULTBY);
    public static final RedisCommand JSON_OBJKEYS            = create(JsonCommand.OBJKEYS);
    public static final RedisCommand JSON_OBJLEN             = create(JsonCommand.OBJLEN);
    public static final RedisCommand JSON_RESP               = create(JsonCommand.RESP);
    public static final RedisCommand JSON_SET                = create(JsonCommand.SET);
    public static final RedisCommand JSON_STRAPPEND          = create(JsonCommand.STRAPPEND);
    public static final RedisCommand JSON_STRLEN             = create(JsonCommand.STRLEN);
    public static final RedisCommand JSON_TOGGLE             = create(JsonCommand.TOGGLE);
    public static final RedisCommand JSON_TYPE               = create(JsonCommand.TYPE);

    /* --------------------------------------------- RediSearch --------------------------------------------- */

    public static final RedisCommand FT__LIST                = create(SearchCommand._LIST);
    public static final RedisCommand FT_AGGREGATE            = create(SearchCommand.AGGREGATE);
    public static final RedisCommand FT_ALIASADD             = create(SearchCommand.ALIASADD);
    public static final RedisCommand FT_ALIASDEL             = create(SearchCommand.ALIASDEL);
    public static final RedisCommand FT_ALIASUPDATE          = create(SearchCommand.ALIASUPDATE);
    public static final RedisCommand FT_ALTER                = create(SearchCommand.ALTER);
    public static final RedisCommand FT_CONFIG_GET           = create(SearchCommand.CONFIG, SearchKeyword.GET);
    public static final RedisCommand FT_CONFIG_SET           = create(SearchCommand.CONFIG, SearchKeyword.SET);
    public static final RedisCommand FT_CREATE               = create(SearchCommand.CREATE);
    public static final RedisCommand FT_CURSOR_DEL           = create(SearchCommand.CURSOR, SearchKeyword.DEL);
    public static final RedisCommand FT_CURSOR_READ          = create(SearchCommand.CURSOR, SearchKeyword.READ);
    public static final RedisCommand FT_DICTADD              = create(SearchCommand.DICTADD);
    public static final RedisCommand FT_DICTDEL              = create(SearchCommand.DICTDEL);
    public static final RedisCommand FT_DICTDUMP             = create(SearchCommand.DICTDUMP);
    public static final RedisCommand FT_DROPINDEX            = create(SearchCommand.DROPINDEX);
    public static final RedisCommand FT_EXPLAIN              = create(SearchCommand.EXPLAIN);
    public static final RedisCommand FT_EXPLAINCLI           = create(SearchCommand.EXPLAINCLI);
    public static final RedisCommand FT_INFO                 = create(SearchCommand.INFO);
    public static final RedisCommand FT_PROFILE              = create(SearchCommand.PROFILE);
    public static final RedisCommand FT_SEARCH               = create(SearchCommand.SEARCH);
    public static final RedisCommand FT_SPELLCHECK           = create(SearchCommand.SPELLCHECK);
    public static final RedisCommand FT_SUGADD               = create(SearchCommand.SUGADD);
    public static final RedisCommand FT_SUGDEL               = create(SearchCommand.SUGDEL);
    public static final RedisCommand FT_SUGGET               = create(SearchCommand.SUGGET);
    public static final RedisCommand FT_SUGLEN               = create(SearchCommand.SUGLEN);
    public static final RedisCommand FT_SYNDUMP              = create(SearchCommand.SYNDUMP);
    public static final RedisCommand FT_SYNUPDATE            = create(SearchCommand.SYNUPDATE);
    public static final RedisCommand FT_TAGVALS              = create(SearchCommand.TAGVALS);

    /* --------------------------------------------- RedisBloom --------------------------------------------- */

    public static final RedisCommand BF_ADD                  = create(BloomFilterCommand.ADD);
    public static final RedisCommand BF_CARD                 = create(BloomFilterCommand.CARD);
    public static final RedisCommand BF_EXISTS               = create(BloomFilterCommand.EXISTS);
    public static final RedisCommand BF_INFO                 = create(BloomFilterCommand.INFO);
    public static final RedisCommand BF_INSERT               = create(BloomFilterCommand.INSERT);
    public static final RedisCommand BF_LOADCHUNK            = create(BloomFilterCommand.LOADCHUNK);
    public static final RedisCommand BF_MADD                 = create(BloomFilterCommand.MADD);
    public static final RedisCommand BF_MEXISTS              = create(BloomFilterCommand.MEXISTS);
    public static final RedisCommand BF_RESERVE              = create(BloomFilterCommand.RESERVE);
    public static final RedisCommand BF_SCANDUMP             = create(BloomFilterCommand.SCANDUMP);

    public static final RedisCommand CF_ADD                  = create(CuckooFilterCommand.ADD);
    public static final RedisCommand CF_ADDNX                = create(CuckooFilterCommand.ADDNX);
    public static final RedisCommand CF_COUNT                = create(CuckooFilterCommand.COUNT);
    public static final RedisCommand CF_DEL                  = create(CuckooFilterCommand.DEL);
    public static final RedisCommand CF_EXISTS               = create(CuckooFilterCommand.EXISTS);
    public static final RedisCommand CF_INFO                 = create(CuckooFilterCommand.INFO);
    public static final RedisCommand CF_INSERT               = create(CuckooFilterCommand.INSERT);
    public static final RedisCommand CF_INSERTNX             = create(CuckooFilterCommand.INSERTNX);
    public static final RedisCommand CF_LOADCHUNK            = create(CuckooFilterCommand.LOADCHUNK);
    public static final RedisCommand CF_MEXISTS              = create(CuckooFilterCommand.MEXISTS);
    public static final RedisCommand CF_RESERVE              = create(CuckooFilterCommand.RESERVE);
    public static final RedisCommand CF_SCANDUMP             = create(CuckooFilterCommand.SCANDUMP);

    public static final RedisCommand CMS_INCRBY              = create(CountMinSketchCommand.INCRBY);
    public static final RedisCommand CMS_INFO                = create(CountMinSketchCommand.INFO);
    public static final RedisCommand CMS_INITBYDIM           = create(CountMinSketchCommand.INITBYDIM);
    public static final RedisCommand CMS_INITBYPROB          = create(CountMinSketchCommand.INITBYPROB);
    public static final RedisCommand CMS_MERGE               = create(CountMinSketchCommand.MERGE);
    public static final RedisCommand CMS_QUERY               = create(CountMinSketchCommand.QUERY);

    public static final RedisCommand TDIGEST_ADD             = create(TDigestCommand.ADD);
    public static final RedisCommand TDIGEST_BYRUNK          = create(TDigestCommand.BYRANK);
    public static final RedisCommand TDIGEST_BYREVRANK       = create(TDigestCommand.BYREVRANK);
    public static final RedisCommand TDIGEST_CDF             = create(TDigestCommand.CDF);
    public static final RedisCommand TDIGEST_CREATE          = create(TDigestCommand.CREATE);
    public static final RedisCommand TDIGEST_INFO            = create(TDigestCommand.INFO);
    public static final RedisCommand TDIGEST_MAX             = create(TDigestCommand.MAX);
    public static final RedisCommand TDIGEST_MERGE           = create(TDigestCommand.MERGE);
    public static final RedisCommand TDIGEST_MIN             = create(TDigestCommand.MIN);
    public static final RedisCommand TDIGEST_QUANTILE        = create(TDigestCommand.QUANTILE);
    public static final RedisCommand TDIGEST_RANK            = create(TDigestCommand.RANK);
    public static final RedisCommand TDIGEST_RESET           = create(TDigestCommand.RESET);
    public static final RedisCommand TDIGEST_REVRANK         = create(TDigestCommand.REVRANK);
    public static final RedisCommand TDIGEST_TRIMMED_MEAN    = create(TDigestCommand.TRIMMED_MEAN);

    public static final RedisCommand TOPK_ADD                = create(TopKCommand.ADD);
    public static final RedisCommand TOPK_COUNT              = create(TopKCommand.COUNT);
    public static final RedisCommand TOPK_INCRBY             = create(TopKCommand.INCRBY);
    public static final RedisCommand TOPK_INFO               = create(TopKCommand.INFO);
    public static final RedisCommand TOPK_LIST               = create(TopKCommand.LIST);
    public static final RedisCommand TOPK_QUERY              = create(TopKCommand.QUERY);
    public static final RedisCommand TOPK_RESERVE            = create(TopKCommand.RESERVE);

    /* --------------------------------------------- RedisTimeSeries --------------------------------------------- */

    public static final RedisCommand TS_ADD                  = create(TimeSeriesCommand.ADD);
    public static final RedisCommand TS_ALTER                = create(TimeSeriesCommand.ALTER);
    public static final RedisCommand TS_CREATE               = create(TimeSeriesCommand.CREATE);
    public static final RedisCommand TS_CREATERULE           = create(TimeSeriesCommand.CREATERULE);
    public static final RedisCommand TS_DECRBY               = create(TimeSeriesCommand.DECRBY);
    public static final RedisCommand TS_DEL                  = create(TimeSeriesCommand.DEL);
    public static final RedisCommand TS_DELETERULE           = create(TimeSeriesCommand.DELETERULE);
    public static final RedisCommand TS_GET                  = create(TimeSeriesCommand.GET);
    public static final RedisCommand TS_INCRBY               = create(TimeSeriesCommand.INCRBY);
    public static final RedisCommand TS_INFO                 = create(TimeSeriesCommand.INFO);
    public static final RedisCommand TS_MADD                 = create(TimeSeriesCommand.MADD);
    public static final RedisCommand TS_MGET                 = create(TimeSeriesCommand.MGET);
    public static final RedisCommand TS_MRANGE               = create(TimeSeriesCommand.MRANGE);
    public static final RedisCommand TS_MREVRANGE            = create(TimeSeriesCommand.MREVRANGE);
    public static final RedisCommand TS_QUERYINDEX           = create(TimeSeriesCommand.QUERYINDEX);
    public static final RedisCommand TS_RANGE                = create(TimeSeriesCommand.RANGE);
    public static final RedisCommand TS_REVRANGE             = create(TimeSeriesCommand.REVRANGE);

    /* ------------------------------------------------------------------------------------------ */

    private static RedisCommand create(@NotNull ProtocolCommand command, @Nullable Rawable keyword) {
        return new RedisCommand(command, getName(command), getName(keyword));
    }

    private static RedisCommand create(@NotNull ProtocolCommand command) {
        return create(command, null);
    }

}