package jdbc.client.structures.query;

import jdbc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CompositeCommand {
    private final Command command;
    private final Keyword commandKeyword;
    private final Keyword resultKeyword;
    private final String[] params;
    private Set<String> paramsSet;

    public CompositeCommand(@NotNull Command command,
                            @Nullable Keyword commandKeyword,
                            @Nullable Keyword resultKeyword,
                            @NotNull String[] params) {
        this.command = command;
        this.commandKeyword = commandKeyword;
        this.resultKeyword = resultKeyword;
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompositeCommand)) return false;
        CompositeCommand c = (CompositeCommand) o;
        return Objects.equals(c.command, command) &&
                Objects.equals(c.commandKeyword, commandKeyword) &&
                Objects.equals(c.resultKeyword, resultKeyword);
    }

    @Override
    public int hashCode() {
        return command.hashCode() ^
                (commandKeyword == null ? 0 : commandKeyword.hashCode()) ^
                (resultKeyword == null ? 0 : resultKeyword.hashCode());
    }

    @NotNull
    public Command getCommand() {
        return command;
    }

    @NotNull
    public String[] getParams() {
        return params;
    }

    public boolean containsParam(@NotNull Keyword keyword) {
        if (paramsSet == null) {
            paramsSet = Arrays.stream(params).map(Utils::toUpperCase).collect(Collectors.toSet());
        }
        return paramsSet.contains(keyword.name());
    }

    public static CompositeCommand create(@NotNull Command command,
                                          @Nullable Keyword commandKeyword,
                                          @Nullable Keyword resultKeyword) {
        return new CompositeCommand(command, commandKeyword, resultKeyword, new String[0]);
    }

    public static CompositeCommand create(@NotNull Command command, @Nullable Keyword commandKeyword) {
        return create(command, commandKeyword, null);
    }

    public static CompositeCommand create(@NotNull Command command) {
        return create(command, null);
    }
}
