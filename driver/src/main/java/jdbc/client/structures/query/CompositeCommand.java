package jdbc.client.structures.query;

import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Objects;

public class CompositeCommand {
    private final Command command;
    private final Keyword keyword;

    CompositeCommand(Command command, Keyword keyword) {
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

    public Command getCommand() {
        return command;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public static CompositeCommand create(Command command, Keyword keyword) {
        return new CompositeCommand(command, keyword);
    }

    public static CompositeCommand create(Command command) {
        return create(command, null);
    }
}
