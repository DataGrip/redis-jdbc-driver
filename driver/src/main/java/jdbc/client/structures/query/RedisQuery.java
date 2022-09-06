package jdbc.client.structures.query;

import redis.clients.jedis.Protocol;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final String[] params;

    public RedisQuery(Protocol.Command command, Protocol.Keyword keyword, String[] params) {
        this.compositeCommand = new CompositeCommand(command, keyword);
        this.params = params;
    }

    public CompositeCommand getCompositeCommand() {
        return compositeCommand;
    }

    public Protocol.Command getCommand() {
        return compositeCommand.getCommand();
    }

    public String[] getParams() {
        return params;
    }
}
