package jdbc.client;

import jdbc.client.impl.cluster.RedisJedisClusterURI;
import jdbc.client.impl.standalone.RedisJedisURI;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;

import java.sql.SQLException;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RedisURITest {

    @Test
    public void testURI() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://user:password@server:6380/7", null);
        HostAndPort hostAndPort = uri.getHostAndPort();
        assertEquals("server", hostAndPort.getHost());
        assertEquals(6380, hostAndPort.getPort());
        assertEquals("user", uri.getUser());
        assertEquals("password", uri.getPassword());
        assertEquals(7, uri.getDatabase());
    }

    @Test
    public void testURIEmptyUser() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://password@server:6380/7", null);
        assertNull(uri.getUser());
        assertEquals("password", uri.getPassword());
    }

    @Test
    public void testURIEmptyAuth() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://server:6380/7", null);
        assertNull(uri.getUser());
        assertNull(uri.getPassword());
    }

    @Test
    public void testURIEmptyDatabase() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://server:6380", null);
        assertEquals(0, uri.getDatabase());
    }

    @Test
    public void testURIEmptyDatabaseAfterSlash() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://user:password@server:6380/", null);
        assertEquals(0, uri.getDatabase());
    }

    @Test
    public void testURIParamsAfterDatabase() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://user:password@server:6380/7?connectionTimeout=1000&socketTimeout=3000", null);
        assertEquals(1000, uri.getConnectionTimeoutMillis());
        assertEquals(3000, uri.getSocketTimeoutMillis());
    }

    @Test
    public void testURIParamsAfterSlash() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://user:password@server:6380/?connectionTimeout=1000&socketTimeout=3000", null);
        assertEquals(1000, uri.getConnectionTimeoutMillis());
        assertEquals(3000, uri.getSocketTimeoutMillis());
    }


    @Test
    public void testURIParamsAfterHostAndPort() throws SQLException {
        RedisJedisURI uri = new RedisJedisURI("jdbc:redis://user:password@server:6380?connectionTimeout=1000&socketTimeout=3000", null);
        assertEquals(1000, uri.getConnectionTimeoutMillis());
        assertEquals(3000, uri.getSocketTimeoutMillis());
    }

    @Test
    public void testClusterURI() throws SQLException {
        RedisJedisClusterURI uri = new RedisJedisClusterURI("jdbc:redis:cluster://user:password@host0:6379,host1:6380,host2:6381,host3:6382,host4:6383,host5:6384?maxAttempts=6", null);
        HostAndPort[] nodes = uri.getNodes().stream().sorted(Comparator.comparing(HostAndPort::getHost)).toArray(HostAndPort[]::new);
        assertEquals(nodes.length, 6);
        for (int i = 0; i < 6; ++i) {
            assertEquals(String.format("host%d", i), nodes[i].getHost());
            assertEquals(6379 + i, nodes[i].getPort());
        }
        assertEquals("user", uri.getUser());
        assertEquals("password", uri.getPassword());
        assertEquals(0, uri.getDatabase());
        assertEquals(6, uri.getMaxAttempts());
    }
}
