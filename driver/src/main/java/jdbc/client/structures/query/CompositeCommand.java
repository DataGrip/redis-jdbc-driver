package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Objects;

public class CompositeCommand {
    private final Command command;
    private final Keyword keyword;

    public CompositeCommand(@NotNull Command command, @Nullable Keyword keyword) {
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

    @NotNull
    public Command getCommand() {
        return command;
    }


    public static CompositeCommand create(@NotNull Command command, @Nullable Keyword keyword) {
        return new CompositeCommand(command, keyword);
    }

    public static CompositeCommand create(@NotNull Command command) {
        return create(command, null);
    }
}
