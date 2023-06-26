package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.args.Rawable;

import java.util.Objects;

public class CompositeCommand {

    private final Command command;
    private final String keyword;

    public CompositeCommand(@NotNull Command command, @Nullable Rawable keyword) {
        this.command = command;
        this.keyword = Utils.getName(keyword);
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

    @Override
    public String toString() {
        return keyword == null ? command.name() : String.format("%s %s", command.name(), keyword);
    }

    public static CompositeCommand create(@NotNull Command command, @Nullable Rawable keyword) {
        return new CompositeCommand(command, keyword);
    }

    public static CompositeCommand create(@NotNull Command command) {
        return create(command, null);
    }
}
