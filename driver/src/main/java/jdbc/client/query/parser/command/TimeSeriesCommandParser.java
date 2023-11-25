package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.timeseries.TimeSeriesProtocol.TimeSeriesCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

public class TimeSeriesCommandParser extends CommandParserWithoutEx<TimeSeriesCommand> {

    private static final Map<String, TimeSeriesCommand> TIME_SERIES_COMMANDS = toMap(TimeSeriesCommand.values());

    TimeSeriesCommandParser() {
        super();
    }

    @Override
    protected @Nullable TimeSeriesCommand parseCommand(@NotNull String commandName) {
        return TIME_SERIES_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}

