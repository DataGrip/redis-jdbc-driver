package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.timeseries.TimeSeriesProtocol.TimeSeriesCommand;

import java.util.Map;

import static jdbc.utils.Utils.toNameMap;

class TimeSeriesCommandParser extends CommandParser {

    private static final Map<String, TimeSeriesCommand> TIME_SERIES_COMMANDS = toNameMap(TimeSeriesCommand.values());

    @Override
    protected @Nullable TimeSeriesCommand parseCommand(@NotNull String commandName) {
        return TIME_SERIES_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}

