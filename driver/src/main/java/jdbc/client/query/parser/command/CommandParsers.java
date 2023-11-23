package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;

public class CommandParsers {

    private CommandParsers() {
    }

    private static final CommandParser<?, ?> NATIVE_COMMAND_PARSER = new NativeCommandParser();
    private static final CommandParser<?, ?> JSON_COMMAND_PARSER = new JsonCommandParser();
    private static final CommandParser<?, ?> SEARCH_COMMAND_PARSER = new SearchCommandParser();
    private static final CommandParser<?, ?> UNKNOWN_COMMAND_PARSER = new UnknownCommandParser();

    public static @NotNull CommandParser<?, ?> get(@NotNull String commandName) {
        if (commandName.contains(".")) {
            if (commandName.startsWith("JSON.")) return JSON_COMMAND_PARSER;
            if (commandName.startsWith("FT.")) return SEARCH_COMMAND_PARSER;
            return UNKNOWN_COMMAND_PARSER;
        }
        return NATIVE_COMMAND_PARSER;
    }
}
