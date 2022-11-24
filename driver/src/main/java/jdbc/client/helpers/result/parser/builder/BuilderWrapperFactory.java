package jdbc.client.helpers.result.parser.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static redis.clients.jedis.BuilderFactory.RAW_OBJECT_LIST;

public class BuilderWrapperFactory {

    private BuilderWrapperFactory() {
    }

    public static final ListBuilderWrapper<Object> RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Object> getBuilder() {
            return BuilderFactory.ENCODED_OBJECT;
        }

        @Override
        protected @NotNull Builder<List<Object>> getListBuilder() {
            return BuilderFactory.ENCODED_OBJECT_LIST;
        }
    };

    public static final ListBuilderWrapper<Long> LONG_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Long> getBuilder() {
            return BuilderFactory.LONG;
        }

        @Override
        protected @NotNull Builder<List<Long>> getListBuilder() {
            return BuilderFactory.LONG_LIST;
        }
    };

    public static final ListBuilderWrapper<Double> DOUBLE_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Double> getBuilder() {
            return BuilderFactory.DOUBLE;
        }

        @Override
        protected @NotNull Builder<List<Double>> getListBuilder() {
            return BuilderFactory.DOUBLE_LIST;
        }
    };

    public static final ListBuilderWrapper<Boolean> BOOLEAN_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Boolean> getBuilder() {
            return BuilderFactory.BOOLEAN;
        }

        @Override
        protected @NotNull Builder<List<Boolean>> getListBuilder() {
            return BuilderFactory.BOOLEAN_LIST;
        }
    };

    public static final ListBuilderWrapper<byte[]> BYTE_ARRAY_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<byte[]> getBuilder() {
            return BuilderFactory.BYTE_ARRAY;
        }

        @Override
        protected @NotNull Builder<List<byte[]>> getListBuilder() {
            return BuilderFactory.BYTE_ARRAY_LIST;
        }
    };

    public static final MapBuilderWrapper<String> STRING_MAP = new SimpleMapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, String>> getMapBuilder() {
            return BuilderFactory.STRING_MAP;
        }
    };

    public static final ListBuilderWrapper<Tuple> TUPLE_RESULT = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<Tuple>> getListBuilder() {
            return BuilderFactory.TUPLE_LIST;
        }
    };

    public static final ListBuilderWrapper<KeyedListElement> KEYED_LIST_ELEMENT_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<KeyedListElement> getBuilder() {
            return BuilderFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final ListBuilderWrapper<KeyedZSetElement> KEYED_ZSET_ELEMENT_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<KeyedZSetElement> getBuilder() {
            return BuilderFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final ListBuilderWrapper<GeoCoordinate> GEO_COORDINATE = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<GeoCoordinate>> getListBuilder() {
            return BuilderFactory.GEO_COORDINATE_LIST;
        }
    };

    public static final ListBuilderWrapper<GeoRadiusResponse> GEORADIUS_RESPONSE = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<GeoRadiusResponse>> getListBuilder() {
            return BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT;
        }
    };

    public static final ListBuilderWrapper<Module> MODULE = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<Module>> getListBuilder() {
            return BuilderFactory.MODULE_LIST;
        }
    };

    public static final ListBuilderWrapper<AccessControlUser> ACCESS_CONTROL_USER = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<AccessControlUser> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ListBuilderWrapper<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<AccessControlLogEntry>> getListBuilder() {
            return BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST;
        }
    };

    public static final MapBuilderWrapper<CommandDocument> COMMAND_DOCUMENT = new SimpleMapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandDocument>> getMapBuilder() {
            return BuilderFactory.COMMAND_DOCS_RESPONSE;
        }
    };

    public static final MapBuilderWrapper<CommandInfo> COMMAND_INFO = new SimpleMapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandInfo>> getMapBuilder() {
            return BuilderFactory.COMMAND_INFO_RESPONSE;
        }
    };

    public static final ListBuilderWrapper<FunctionStats> FUNCTION_STATS = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<FunctionStats> getBuilder() {
            return FunctionStats.FUNCTION_STATS_BUILDER;
        }
    };

    public static final ListBuilderWrapper<LibraryInfo> LIBRARY_INFO = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<LibraryInfo>> getListBuilder() {
            return BuilderFactory.LIBRARY_LIST;
        }
    };

    // TODO: LCSMatchResult (MatchedPosition, Position)

    public static final ListBuilderWrapper<Slowlog> SLOW_LOG = new SimpleListBuilderWrapper<>() {
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

    public static final ListBuilderWrapper<StreamEntryID> STREAM_ENTRY_ID = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<StreamEntryID> getBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull Builder<List<StreamEntryID>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final ListBuilderWrapper<StreamEntry> STREAM_ENTRY = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<StreamEntry>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final ListBuilderWrapper<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<Map.Entry<String, List<StreamEntry>>>> getListBuilder() {
            return BuilderFactory.STREAM_READ_RESPONSE;
        }
    };

    public static final ListBuilderWrapper<StreamConsumersInfo> STREAM_CONSUMER_INFO = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<StreamConsumersInfo>> getListBuilder() {
            return BuilderFactory.STREAM_CONSUMERS_INFO_LIST;
        }
    };

    public static final ListBuilderWrapper<StreamGroupInfo> STREAM_GROUP_INFO = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<StreamGroupInfo>> getListBuilder() {
            return BuilderFactory.STREAM_GROUP_INFO_LIST;
        }
    };

    public static final ListBuilderWrapper<StreamInfo> STREAM_INFO = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<StreamInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final ListBuilderWrapper<StreamFullInfo> STREAM_INFO_FULL = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<StreamFullInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO_FULL;
        }
    };

    public static final ListBuilderWrapper<StreamPendingEntry> STREAM_PENDING_ENTRY = new SimpleListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<List<StreamPendingEntry>> getListBuilder() {
            return BuilderFactory.STREAM_PENDING_ENTRY_LIST;
        }
    };

    public static final ListBuilderWrapper<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<StreamPendingSummary> getBuilder() {
            return BuilderFactory.STREAM_PENDING_SUMMARY;
        }
    };

    public static final ListBuilderWrapper<ScanResult<String>> STRING_SCAN_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<ScanResult<String>> getBuilder() {
            return BuilderFactory.SCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<String>";
        }
    };

    public static final ListBuilderWrapper<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<ScanResult<Tuple>> getBuilder() {
            return BuilderFactory.ZSCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<Tuple>";
        }
    };

    public static final ListBuilderWrapper<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ElementListBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<ScanResult<Map.Entry<String, String>>> getBuilder() {
            return BuilderFactory.HSCAN_RESPONSE;
        }

        @Override
        public String toString() {
            return "ScanResult<Map.Entry<String, String>>";
        }
    };


    private static abstract class ElementListBuilderWrapper<T> extends ListBuilderWrapper<T> {

        protected abstract @NotNull Builder<T> getBuilder();
        
        protected @Nullable Builder<List<T>> getListBuilder() {
            return null;
        }

        @Override
        public @NotNull List<T> build(@Nullable Object data) {
            if (data == null) return Collections.singletonList(null);
            Builder<List<T>> listBuilder = getListBuilder();
            if (listBuilder != null && data instanceof List) return listBuilder.build(data);
            return Collections.singletonList(getBuilder().build(data));
        }

        @Override
        public String toString() {
            return String.format("List<%s>", getBuilder());
        }
    }

    private abstract static class SimpleListBuilderWrapper<T> extends ListBuilderWrapper<T> {

        protected abstract @NotNull Builder<List<T>> getListBuilder();

        @Override
        public @NotNull List<T> build(Object data) {
            if (data == null) return Collections.emptyList();
            return getListBuilder().build(data);
        }

        @Override
        public String toString() {
            return getListBuilder().toString();
        }
    }

    private abstract static class SimpleMapBuilderWrapper<T> extends MapBuilderWrapper<T> {

        protected abstract @NotNull Builder<Map<String, T>> getMapBuilder();

        @Override
        public @NotNull Map<String, T> build(Object data) {
            if (data == null) return Collections.emptyMap();
            return getMapBuilder().build(data);
        }

        @Override
        public String toString() {
            return getMapBuilder().toString();
        }
    }
}
