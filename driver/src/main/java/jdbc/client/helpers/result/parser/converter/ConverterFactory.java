package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterFactory {

    private ConverterFactory() {
    }

    public static final ObjectConverter<Tuple> TUPLE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull Tuple encoded) {
            return new HashMap<>() {{
                put("value", encoded.getElement());
                put("score", encoded.getScore());
            }};
        }
    };

    public static final ObjectConverter<KeyedListElement> KEYED_LIST_ELEMENT = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull KeyedListElement encoded) {
            return new HashMap<>() {{
                put("key", encoded.getKey());
                put("value", encoded.getElement());
            }};
        }
    };

    public static final ObjectConverter<KeyedZSetElement> KEYED_ZSET_ELEMENT = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull KeyedZSetElement encoded) {
            return new HashMap<>() {{
                put("key", encoded.getKey());
                put("value", encoded.getElement());
                put("score", encoded.getScore());
            }};
        }
    };

    public static final ObjectConverter<GeoCoordinate> GEO_COORDINATE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull GeoCoordinate encoded) {
            return new HashMap<>() {{
                put("longitude", encoded.getLongitude());
                put("latitude", encoded.getLatitude());
            }};
        }
    };

    public static final ObjectConverter<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull GeoRadiusResponse encoded) {
            return new HashMap<>() {{
                put("member", encoded.getMemberByString());
                put("distance", encoded.getDistance());
                put("coordinate", GEO_COORDINATE.convert(encoded.getCoordinate()));
                put("raw-score", encoded.getRawScore());
            }};
        }
    };

    public static final ObjectConverter<Module> MODULE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull Module encoded) {
            return new HashMap<>() {{
                put("name", encoded.getName());
                put("version", encoded.getVersion());
            }};
        }
    };

    public static final ObjectConverter<AccessControlUser> ACCESS_CONTROL_USER = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull AccessControlUser encoded) {
            return new HashMap<>() {{
                put("flags", encoded.getFlags());
                put("keys", encoded.getKeys());
                put("passwords", encoded.getPassword());
                put("commands", encoded.getCommands());
            }};
        }
    };

    public static final ObjectConverter<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull AccessControlLogEntry encoded) {
            return new HashMap<>() {{
                put("count", encoded.getCount());
                put("reason", encoded.getReason());
                put("context", encoded.getContext());
                put("object", encoded.getObject());
                put("username", encoded.getUsername());
                put("age-seconds", encoded.getAgeSeconds());
                put("client-info", encoded.getClientInfo());
            }};
        }
    };

    public static final ObjectConverter<CommandDocument> COMMAND_DOCUMENT = new ObjectConverter<>() {
        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull CommandDocument encoded) {
            return new HashMap<>() {{
                put("summary", encoded.getSummary());
                put("since", encoded.getSince());
                put("group", encoded.getGroup());
                put("complexity", encoded.getComplexity());
                put("history", encoded.getHistory());
            }};
        }

        @Override
        protected @NotNull Map<String, Object> convertEntryImpl(@NotNull Map.Entry<String, CommandDocument> encoded) {
            Map<String, Object> converted = super.convertEntryImpl(encoded);
            converted.put("command-name", encoded.getKey());
            return converted;
        }
    };

    public static final ObjectConverter<CommandInfo> COMMAND_INFO = new ObjectConverter<>() {
        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull CommandInfo encoded) {
            return new HashMap<>() {{
                put("arity", encoded.getArity());
                put("flags", encoded.getFlags());
                put("firstKey", encoded.getFirstKey());
                put("lastKey", encoded.getLastKey());
                put("step", encoded.getStep());
                put("acl-categories", encoded.getAclCategories());
                put("tips", encoded.getTips());
                put("subcommands", encoded.getSubcommands());
            }};
        }

        @Override
        protected @NotNull Map<String, Object> convertEntryImpl(@NotNull Map.Entry<String, CommandInfo> encoded) {
            Map<String, Object> converted = super.convertEntryImpl(encoded);
            converted.put("command-name", encoded.getKey());
            return converted;
        }
    };

    public static final ObjectConverter<FunctionStats> FUNCTION_STATS = new ObjectConverter<>() {
        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull FunctionStats encoded) {
            return new HashMap<>() {{
                put("running-script", encoded.getRunningScript());
                put("engines", encoded.getEngines());
            }};
        }
    };

    public static final ObjectConverter<LibraryInfo> LIBRARY_INFO = new ObjectConverter<>() {
        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull LibraryInfo encoded) {
            return new HashMap<>() {{
                put("library-name", encoded.getLibraryName());
                put("engine", encoded.getEngine());
                put("functions", encoded.getFunctions());
                put("library-code", encoded.getLibraryCode());
            }};
        }
    };

    public static final ObjectConverter<Slowlog> SLOW_LOG = new ObjectConverter<>() {
        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull Slowlog encoded) {
            return new HashMap<>() {{
                put("id", encoded.getId());
                put("timestamp", encoded.getTimeStamp());
                put("execution-time", encoded.getExecutionTime());
                put("args", encoded.getArgs());
                // TODO: host and port converter?
                put("client-ip-port", encoded.getClientIpPort().toString());
                put("client-name", encoded.getClientName());
            }};
        }
    };

    public static final SimpleConverter<StreamEntryID> STREAM_ENTRY_ID = new SimpleConverter<>() {
        @Override
        public @NotNull Object convertImpl(@NotNull StreamEntryID encoded) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<StreamEntry> STREAM_ENTRY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamEntry encoded) {
            return new HashMap<>() {{
                put("id", STREAM_ENTRY_ID.convert(encoded.getID()));
                put("fields", encoded.getFields());
            }};
        }
    };

    public static final ObjectConverter<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull Map.Entry<String, List<StreamEntry>> encoded) {
            return new HashMap<>() {{
                put("key", encoded.getKey());
                put("entries", STREAM_ENTRY.convert(encoded.getValue()));
            }};
        }
    };

    public static final ObjectConverter<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamGroupInfo encoded) {
            return new HashMap<>() {{
                put("name", encoded.getName());
                put("consumers", encoded.getConsumers());
                put("pending", encoded.getPending());
                put("last-delivered-id", STREAM_ENTRY_ID.convert(encoded.getLastDeliveredId()));
            }};
        }
    };

    public static final ObjectConverter<StreamConsumersInfo> STREAM_CONSUMERS_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamConsumersInfo encoded) {
            return new HashMap<>() {{
                put("name", encoded.getName());
                put("idle", encoded.getIdle());
                put("pending", encoded.getPending());
            }};
        }
    };

    public static final ObjectConverter<StreamInfo> STREAM_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamInfo encoded) {
            return new HashMap<>() {{
                put("length", encoded.getLength());
                put("radix-tree-keys", encoded.getRadixTreeKeys());
                put("radix-tree-nodes", encoded.getRadixTreeNodes());
                put("groups", encoded.getGroups());
                put("last-generated-id", STREAM_ENTRY_ID.convert(encoded.getLastGeneratedId()));
                put("first-entry", STREAM_ENTRY.convert(encoded.getFirstEntry()));
                put("last-entry", STREAM_ENTRY.convert(encoded.getLastEntry()));
            }};
        }
    };

    public static final ObjectConverter<StreamFullInfo> STREAM_INFO_FULL = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamFullInfo encoded) {
            return new HashMap<>() {{
                put("length", encoded.getLength());
                put("radix-tree-keys", encoded.getRadixTreeKeys());
                put("radix-tree-nodes", encoded.getRadixTreeNodes());
                // TODO: STREAM_GROUP_FULL_INFO + STREAM_CONSUMER_FULL_INFO  Converters
                put("groups", encoded.getGroups());
                put("last-generated-id", STREAM_ENTRY_ID.convert(encoded.getLastGeneratedId()));
                put("entries", STREAM_ENTRY.convert(encoded.getEntries()));
            }};
        }
    };

    public static final ObjectConverter<StreamPendingEntry> STREAM_PENDING_ENTRY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamPendingEntry encoded) {
            return new HashMap<>() {{
                put("id", STREAM_ENTRY_ID.convert(encoded.getID()));
                put("consumer-name", encoded.getConsumerName());
                put("idle-time", encoded.getIdleTime());
                put("delivered-times", encoded.getDeliveredTimes());
            }};
        }
    };

    public static final ObjectConverter<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamPendingSummary encoded) {
            return new HashMap<>() {{
                put("total", encoded.getTotal());
                put("min-id", STREAM_ENTRY_ID.convert(encoded.getMinId()));
                put("max-id", STREAM_ENTRY_ID.convert(encoded.getMaxId()));
                put("consumer-message-count", encoded.getConsumerMessageCount());
            }};
        }
    };
    
    public static final ObjectConverter<ScanResult<String>> STRING_SCAN_RESULT = new ScanResultConverter<>() {
        private final Converter<String, ?, ?> STRING = new IdentityConverter<>();

        @Override
        protected @NotNull Converter<String, ?, ?> getResultsConvertor() {
            return STRING;
        }
    };

    public static final ObjectConverter<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ScanResultConverter<>() {
        @Override
        protected @NotNull Converter<Tuple, ?, ?> getResultsConvertor() {
            return TUPLE;
        }
    };

    public static final ObjectConverter<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ScanResultConverter<>() {
        private final Converter<Map.Entry<String, String>, ?, ?> ENTRY = new ObjectConverter<>() {
            @Override
            protected @NotNull Map<String, Object> convertImpl(Map.@NotNull Entry<String, String> encoded) {
                return new HashMap<>() {{
                    put("field", encoded.getKey());
                    put("value", encoded.getValue());
                }};
            }
        };

        @Override
        protected @NotNull Converter<Map.Entry<String, String>, ?, ?> getResultsConvertor() {
            return ENTRY;
        }
    };

    private abstract static class ScanResultConverter<T> extends ObjectConverter<ScanResult<T>> {

        protected abstract @NotNull Converter<T, ?, ?> getResultsConvertor();

        @Override
        protected @NotNull Map<String, Object> convertImpl(@NotNull ScanResult<T> encoded) {
            return new HashMap<>() {{
                put("cursor", encoded.getCursor());
                put("results", getResultsConvertor().convert(encoded.getResult()));
            }};
        }
    }
}
