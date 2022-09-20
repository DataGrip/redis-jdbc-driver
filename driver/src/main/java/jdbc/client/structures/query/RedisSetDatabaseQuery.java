package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Protocol.Command;

public class RedisSetDatabaseQuery extends RedisQuery {

    private final int dbIndex;

    public RedisSetDatabaseQuery(int dbIndex) {
        super(Command.SELECT, null, new String[]{Integer.toString(dbIndex)});
        this.dbIndex = dbIndex;
    }

    @NotNull
    public Integer getDbIndex() {
        return dbIndex;
    }
}
