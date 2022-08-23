package jdbc;

import redis.clients.jedis.Protocol;

public class RedisQuery {

    private final Protocol.Command command;
    private final String[] params;

    public RedisQuery(Protocol.Command command, String[] params) {
        this.command = command;
        this.params = params;
    }

    public Protocol.Command getCommand() {
        return command;
    }

    public String[] getParams() {
        return params;
    }
}
