package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CommandParsers {

    private CommandParsers() {
    }


    private static final CommandParser NATIVE_COMMAND_PARSER = new NativeCommandParser();

    private static final Map<String, CommandParser> MODULE_COMMAND_PARSERS = new HashMap<>();

    static {
        MODULE_COMMAND_PARSERS.put("JSON",    new JsonCommandParser());
        MODULE_COMMAND_PARSERS.put("FT",      new SearchCommandParser());
        MODULE_COMMAND_PARSERS.put("BF",      new BloomFilterCommandParser());
        MODULE_COMMAND_PARSERS.put("CF",      new CuckooFilterCommandParser());
        MODULE_COMMAND_PARSERS.put("CMS",     new CountMinSketchCommandParser());
        MODULE_COMMAND_PARSERS.put("TDIGEST", new TDigestCommandParser());
        MODULE_COMMAND_PARSERS.put("TOPK",    new TopKCommandParser());
        MODULE_COMMAND_PARSERS.put("TS",      new TimeSeriesCommandParser());
    }


    public static @Nullable CommandParser get(@NotNull String commandName) {
        if (commandName.contains(".")) {
            String moduleName = commandName.substring(0, commandName.indexOf("."));
            return MODULE_COMMAND_PARSERS.get(moduleName);
        }
        return NATIVE_COMMAND_PARSER;
    }
}
