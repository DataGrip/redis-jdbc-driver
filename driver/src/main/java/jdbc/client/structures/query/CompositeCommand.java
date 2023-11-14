package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Objects;

public class CompositeCommand {

    private final ProtocolCommand command;

    private final String commandName;
    private final String keywordName;

    public CompositeCommand(@NotNull ProtocolCommand command, @Nullable Rawable keyword) {
        this.command = command;
        this.commandName = Utils.getName(command);
        this.keywordName = Utils.getName(keyword);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompositeCommand)) return false;
        CompositeCommand c = (CompositeCommand) o;
        return Objects.equals(c.command, command) && Objects.equals(c.keywordName, keywordName);
    }

    @Override
    public int hashCode() {
        return command.hashCode() ^ (keywordName == null ? 0 : keywordName.hashCode());
    }

    @NotNull
    public ProtocolCommand getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return keywordName == null ? commandName : String.format("%s %s", commandName, keywordName);
    }

    public static CompositeCommand create(@NotNull ProtocolCommand command, @Nullable Rawable keyword) {
        return new CompositeCommand(command, keyword);
    }

    public static CompositeCommand create(@NotNull ProtocolCommand command) {
        return create(command, null);
    }
}
