package jdbc.client.impl.cluster;

import jdbc.client.impl.RedisClientBase;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.ClusterCommandArguments;
import redis.clients.jedis.CommandArguments;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

public class RedisJedisClusterClient extends RedisClientBase {

    private final JedisCluster jedisCluster;

    public RedisJedisClusterClient(@NotNull RedisJedisClusterURI uri) throws SQLException {
        try {
            jedisCluster = new JedisCluster(uri.getNodes(), uri, uri.getMaxAttempts(), new SingleConnectionPoolConfig());
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected synchronized Object execute(@NotNull RedisQuery query) {
        ClusterQuery clusterQuery = new ClusterQuery(query.getCommand(), query.getForcedSlot());
        clusterQuery.addObjects((Object[]) query.getParams());
        if (query.isBlocking()) clusterQuery.blocking();
        // TODO (cluster): clusterQuery.processKey();
        return jedisCluster.executeCommand(clusterQuery);
    }

    @Override
    protected String setDatabase(int index) {
        if (index == 0) return "OK";
        throw new JedisDataException("ERR SELECT is not allowed in cluster mode");
    }

    @Override
    public String getDatabase() {
        return "0";
    }

    @Override
    public synchronized void doClose() {
        jedisCluster.close();
    }


    private static class ClusterQuery extends ClusterCommandArguments {

        private final Integer forcedSlot;

        ClusterQuery(@NotNull ProtocolCommand command, @Nullable Integer forcedSlot) {
            super(command);
            this.forcedSlot = forcedSlot;
        }

        @Override
        public int getCommandHashSlot() {
            return forcedSlot != null ? forcedSlot : super.getCommandHashSlot();
        }

        @Override
        protected CommandArguments processKey(byte[] key) {
            return forcedSlot != null ? this : super.processKey(key);
        }

        @Override
        protected CommandArguments processKey(String key) {
            return forcedSlot != null ? this : super.processKey(key);
        }
    }
}