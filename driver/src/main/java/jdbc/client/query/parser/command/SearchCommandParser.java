package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.search.SearchProtocol.SearchCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

class SearchCommandParser extends CommandParserWithoutEx<SearchCommand> {

    private static final Map<String, SearchCommand> SEARCH_COMMANDS = toMap(SearchCommand.values());

    @Override
    protected @Nullable SearchCommand parseCommand(@NotNull String commandName) {
        return SEARCH_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return command == SearchCommand.CONFIG || command == SearchCommand.CURSOR;
    }
}
