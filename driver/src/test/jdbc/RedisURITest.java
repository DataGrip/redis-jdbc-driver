package jdbc;

import jdbc.client.RedisURI;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;

import static org.junit.Assert.assertEquals;

public class RedisURITest {
    @Test
    public void testSingleURI() {
        RedisURI uri = new RedisURI("jdbc:redis://user:password@server:6380/7", null);
        HostAndPort hostAndPort = uri.getHostAndPort();
        assertEquals("server", hostAndPort.getHost());
        assertEquals(6380, hostAndPort.getPort());
        assertEquals("user", uri.getUser());
        assertEquals("password", uri.getPassword());
        assertEquals(7, uri.getDatabase());
    }
}
