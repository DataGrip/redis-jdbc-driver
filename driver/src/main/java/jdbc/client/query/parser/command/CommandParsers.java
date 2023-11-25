package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;

public class CommandParsers {

    private CommandParsers() {
    }

    // TODO (anya) [stack]: improve
    private static final CommandParser<?, ?> NATIVE_COMMAND_PARSER = new NativeCommandParser();
    private static final CommandParser<?, ?> JSON_COMMAND_PARSER = new JsonCommandParser();
    private static final CommandParser<?, ?> SEARCH_COMMAND_PARSER = new SearchCommandParser();
    private static final CommandParser<?, ?> BLOOM_FILTER_COMMAND_PARSER = new BloomFilterCommandParser();
    private static final CommandParser<?, ?> CUCKOO_FILTER_COMMAND_PARSER = new CuckooFilterCommandParser();
    private static final CommandParser<?, ?> COUNT_MIN_SKETCH_COMMAND_PARSER = new CountMinSketchCommandParser();
    private static final CommandParser<?, ?> T_DIGEST_COMMAND_PARSER = new TDigestCommandParser();
    private static final CommandParser<?, ?> TOP_K_COMMAND_PARSER = new TopKCommandParser();
    private static final CommandParser<?, ?> UNKNOWN_COMMAND_PARSER = new UnknownCommandParser();

    public static @NotNull CommandParser<?, ?> get(@NotNull String commandName) {
        if (commandName.contains(".")) {
            if (commandName.startsWith("JSON.")) return JSON_COMMAND_PARSER;
            if (commandName.startsWith("FT.")) return SEARCH_COMMAND_PARSER;
            if (commandName.startsWith("BF.")) return BLOOM_FILTER_COMMAND_PARSER;
            if (commandName.startsWith("CF.")) return CUCKOO_FILTER_COMMAND_PARSER;
            if (commandName.startsWith("CMS.")) return COUNT_MIN_SKETCH_COMMAND_PARSER;
            if (commandName.startsWith("TDIGEST.")) return T_DIGEST_COMMAND_PARSER;
            if (commandName.startsWith("TOPK.")) return TOP_K_COMMAND_PARSER;
            return UNKNOWN_COMMAND_PARSER;
        }
        return NATIVE_COMMAND_PARSER;
    }
}
