package jdbc.client.result.parser.encoder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.json.DefaultGsonObjectMapper;
import redis.clients.jedis.json.JsonObjectMapper;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.util.KeyValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static redis.clients.jedis.BuilderFactory.RAW_OBJECT_LIST;

public class EncoderFactory {

    private EncoderFactory() {
    }


    /* --------------------------------------------- Common --------------------------------------------- */

    public static final ListEncoder<Object> OBJECT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Object> getBuilder() {
            return BuilderFactory.ENCODED_OBJECT;
        }

        @Override
        protected @NotNull Builder<List<Object>> getListBuilder() {
            return BuilderFactory.ENCODED_OBJECT_LIST;
        }
    };

    public static final ListEncoder<String> STRING = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<String> getBuilder() {
            return BuilderFactory.STRING;
        }

        @Override
        protected @NotNull Builder<List<String>> getListBuilder() {
            return BuilderFactory.STRING_LIST;
        }
    };

    public static final ListEncoder<Long> LONG = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Long> getBuilder() {
            return BuilderFactory.LONG;
        }

        @Override
        protected @NotNull Builder<List<Long>> getListBuilder() {
            return BuilderFactory.LONG_LIST;
        }
    };

    public static final ListEncoder<Double> DOUBLE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Double> getBuilder() {
            return BuilderFactory.DOUBLE;
        }

        @Override
        protected @NotNull Builder<List<Double>> getListBuilder() {
            return BuilderFactory.DOUBLE_LIST;
        }
    };

    public static final ListEncoder<Boolean> BOOLEAN = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Boolean> getBuilder() {
            return BuilderFactory.BOOLEAN;
        }

        @Override
        protected @NotNull Builder<List<Boolean>> getListBuilder() {
            return BuilderFactory.BOOLEAN_LIST;
        }
    };

    public static final ListEncoder<byte[]> BYTE_ARRAY = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<byte[]> getBuilder() {
            return BuilderFactory.BYTE_ARRAY;
        }

        @Override
        protected @NotNull Builder<List<byte[]>> getListBuilder() {
            return BuilderFactory.BYTE_ARRAY_LIST;
        }
    };


    public static final ListEncoder<List<Object>> OBJECT_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<List<Object>> getBuilder() {
            return BuilderFactory.ENCODED_OBJECT_LIST;
        }

        @Override
        protected @NotNull Builder<List<List<Object>>> getListBuilder() {
            return BuilderFactory.ENCODED_OBJECT_LIST_LIST;
        }
    };

    public static final ListEncoder<List<String>> STRING_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<List<String>> getBuilder() {
            return BuilderFactory.STRING_LIST;
        }

        @Override
        protected @NotNull Builder<List<List<String>>> getListBuilder() {
            return BuilderFactory.STRING_LIST_LIST;
        }
    };


    public static final MapEncoder<Object> OBJECT_MAP = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, Object>> getMapBuilder() {
            return BuilderFactory.ENCODED_OBJECT_MAP;
        }
    };

    public static final MapEncoder<String> STRING_MAP = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, String>> getMapBuilder() {
            return BuilderFactory.STRING_MAP;
        }
    };


    public static final ListEncoder<KeyedListElement> KEYED_STRING = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyedListElement> getBuilder() {
            return BuilderFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final ListEncoder<Tuple> TUPLE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<Tuple>> getListBuilder() {
            return BuilderFactory.TUPLE_LIST;
        }
    };

    public static final ListEncoder<KeyedZSetElement> KEYED_TUPLE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyedZSetElement> getBuilder() {
            return BuilderFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final ListEncoder<GeoCoordinate> GEO_COORDINATE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<GeoCoordinate>> getListBuilder() {
            return BuilderFactory.GEO_COORDINATE_LIST;
        }
    };

    public static final ListEncoder<GeoRadiusResponse> GEORADIUS_RESPONSE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<GeoRadiusResponse>> getListBuilder() {
            return BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT;
        }
    };

    public static final ListEncoder<Module> MODULE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<Module>> getListBuilder() {
            return BuilderFactory.MODULE_LIST;
        }
    };

    public static final ListEncoder<AccessControlUser> ACCESS_CONTROL_USER = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<AccessControlUser> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ListEncoder<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<AccessControlLogEntry>> getListBuilder() {
            return BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST;
        }
    };

    public static final MapEncoder<CommandDocument> COMMAND_DOCUMENT = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandDocument>> getMapBuilder() {
            return BuilderFactory.COMMAND_DOCS_RESPONSE;
        }
    };

    public static final MapEncoder<CommandInfo> COMMAND_INFO = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandInfo>> getMapBuilder() {
            return BuilderFactory.COMMAND_INFO_RESPONSE;
        }
    };

    public static final ListEncoder<FunctionStats> FUNCTION_STATS = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<FunctionStats> getBuilder() {
            return FunctionStats.FUNCTION_STATS_BUILDER;
        }
    };

    public static final ListEncoder<LibraryInfo> LIBRARY_INFO = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<LibraryInfo>> getListBuilder() {
            return BuilderFactory.LIBRARY_LIST;
        }
    };

    public static final ListEncoder<Slowlog> SLOW_LOG = new SimpleListEncoder<>() {

        private final Builder<List<Slowlog>> SLOW_LOG_LIST = new Builder<>() {
            @Override
            public List<Slowlog> build(Object data) {
                return Slowlog.from(RAW_OBJECT_LIST.build(data));
            }

            @Override
            public String toString() {
                return "List<SlowLog>";
            }
        };

        @Override
        protected @NotNull Builder<List<Slowlog>> getListBuilder() {
            return SLOW_LOG_LIST;
        }
    };

    public static final ListEncoder<StreamEntryID> STREAM_ENTRY_ID = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamEntryID> getBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull Builder<List<StreamEntryID>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final ListEncoder<StreamEntry> STREAM_ENTRY = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<StreamEntry>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final ListEncoder<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<Map.Entry<String, List<StreamEntry>>>> getListBuilder() {
            return BuilderFactory.STREAM_READ_RESPONSE;
        }
    };

    public static final ListEncoder<StreamConsumersInfo> STREAM_CONSUMER_INFO = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<StreamConsumersInfo>> getListBuilder() {
            return BuilderFactory.STREAM_CONSUMERS_INFO_LIST;
        }
    };

    public static final ListEncoder<StreamGroupInfo> STREAM_GROUP_INFO = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<StreamGroupInfo>> getListBuilder() {
            return BuilderFactory.STREAM_GROUP_INFO_LIST;
        }
    };

    public static final ListEncoder<StreamInfo> STREAM_INFO = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final ListEncoder<StreamFullInfo> STREAM_INFO_FULL = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamFullInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO_FULL;
        }
    };

    public static final ListEncoder<StreamPendingEntry> STREAM_PENDING_ENTRY = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<StreamPendingEntry>> getListBuilder() {
            return BuilderFactory.STREAM_PENDING_ENTRY_LIST;
        }
    };

    public static final ListEncoder<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamPendingSummary> getBuilder() {
            return BuilderFactory.STREAM_PENDING_SUMMARY;
        }
    };


    public static final ListEncoder<KeyValue<String, List<String>>> KEYED_STRING_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyValue<String, List<String>>> getBuilder() {
            return BuilderFactory.KEYED_STRING_LIST;
        }

        @Override
        protected @NotNull Builder<List<KeyValue<String, List<String>>>> getListBuilder() {
            return BuilderFactory.KEYED_STRING_LIST_LIST;
        }
    };

    public static final ListEncoder<KeyValue<String, List<Tuple>>> KEYED_TUPLE_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyValue<String, List<Tuple>>> getBuilder() {
            return BuilderFactory.KEYED_TUPLE_LIST;
        }
    };


    public static final ListEncoder<ScanResult<String>> STRING_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<String>> getBuilder() {
            return BuilderFactory.SCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<String>";
        }
    };

    public static final ListEncoder<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<Tuple>> getBuilder() {
            return BuilderFactory.ZSCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<Tuple>";
        }
    };

    public static final ListEncoder<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<Map.Entry<String, String>>> getBuilder() {
            return BuilderFactory.HSCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<Map.Entry<String, String>>";
        }
    };


    /* --------------------------------------------- RedisJSON --------------------------------------------- */

    public static final ListEncoder<Object> JSON_OBJECT = new ElementListEncoder<>() {

        private final Builder<Object> JSON_OBJECT = new Builder<>() {
            private final JsonObjectMapper JSON_OBJECT_MAPPER = new DefaultGsonObjectMapper();

            @Override
            public Object build(Object data) {
                return JSON_OBJECT_MAPPER.fromJson(BuilderFactory.STRING.build(data), Object.class);
            }
        };

        private final Builder<List<Object>> JSON_OBJECT_LIST = new Builder<>() {
            @Override
            public List<Object> build(Object data) {
                return ((List<?>) data).stream().map(JSON_OBJECT::build).collect(Collectors.toList());
            }
        };

        @Override
        protected @NotNull Builder<Object> getBuilder() {
            return JSON_OBJECT;
        }

        @Override
        protected @NotNull Builder<List<Object>> getListBuilder() {
            return JSON_OBJECT_LIST;
        }

        @Override
        public String toString() {
            return "List<JsonObject>";
        }
    };


    /* ------------------------------------------------------------------------------------------ */

    private abstract static class SimpleListEncoder<T> extends ListEncoder<T> {

        protected abstract @NotNull Builder<List<T>> getListBuilder();

        @Override
        public final @NotNull List<T> encode(Object data) {
            if (data == null) return Collections.emptyList();
            return getListBuilder().build(data);
        }

        @Override
        public String toString() {
            return getListBuilder().toString();
        }
    }

    private abstract static class SimpleMapEncoder<T> extends MapEncoder<T> {

        protected abstract @NotNull Builder<Map<String, T>> getMapBuilder();

        @Override
        public final @NotNull Map<String, T> encode(Object data) {
            if (data == null) return Collections.emptyMap();
            return getMapBuilder().build(data);
        }

        @Override
        public String toString() {
            return getMapBuilder().toString();
        }
    }

    private static abstract class ElementListEncoder<T> extends ListEncoder<T> {

        protected abstract @NotNull Builder<T> getBuilder();

        protected @Nullable Builder<List<T>> getListBuilder() {
            return null;
        }

        protected boolean isElementList(@NotNull Object data) {
            return getListBuilder() != null && data instanceof List;
        }

        @Override
        public @NotNull List<T> encode(@Nullable Object data) {
            if (data == null) return Collections.singletonList(null);
            if (isElementList(data)) return Objects.requireNonNull(getListBuilder()).build(data);
            return Collections.singletonList(getBuilder().build(data));
        }

        @Override
        public String toString() {
            return String.format("List<%s>", getBuilder());
        }
    }

    private static abstract class ListElementListEncoder<T> extends ElementListEncoder<T> {
        @Override
        protected boolean isElementList(@NotNull Object data) {
            if (!super.isElementList(data)) return false;
            List<?> dataList = (List<?>) data;
            return dataList.isEmpty() || dataList.get(0) instanceof List;
        }
    }
}
