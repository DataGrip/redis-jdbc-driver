package jdbc.client.helpers;

import redis.clients.jedis.Protocol;

import java.util.Objects;

public class RedisQuery {

    static class CompositeCommand {
        private final Protocol.Command command;
        private final Protocol.Keyword keyword;

        private CompositeCommand(Protocol.Command command, Protocol.Keyword keyword) {
            this.command = command;
            this.keyword = keyword;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CompositeCommand)) return false;
            CompositeCommand c = (CompositeCommand) o;
            return Objects.equals(c.command, command) && Objects.equals(c.keyword, keyword);
        }

        @Override
        public int hashCode() {
            return command.hashCode() ^ (keyword == null ? 0 : keyword.hashCode());
        }

        public static CompositeCommand create(Protocol.Command command, Protocol.Keyword keyword) {
            return new CompositeCommand(command, keyword);
        }

        public static CompositeCommand create(Protocol.Command command) {
            return create(command, null);
        }
    }

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
        return compositeCommand.command;
    }

    public String[] getParams() {
        return params;
    }
}
