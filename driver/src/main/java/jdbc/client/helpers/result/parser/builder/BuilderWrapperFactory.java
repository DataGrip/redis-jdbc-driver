package jdbc.client.helpers.result.parser.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.*;

import java.util.*;

import static redis.clients.jedis.BuilderFactory.RAW_OBJECT_LIST;

public class BuilderWrapperFactory {

    private BuilderWrapperFactory() {
    }

    public static final BuilderWrapper<List<Object>> RESULT = new BuilderWrapper<>() {
        @Override
        @SuppressWarnings("unchecked")
        public @NotNull List<Object> build(Object data) {
            Object encoded = BuilderFactory.ENCODED_OBJECT.build(data);
            if (encoded instanceof List) return (List<Object>) encoded;
            return Collections.singletonList(encoded);
        }
    };

    public static final BuilderWrapper<List<Long>> LONG_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<Long> getBuilder() {
            return BuilderFactory.LONG;
        }

        @Override
        protected Builder<List<Long>> getListBuilder() {
            return BuilderFactory.LONG_LIST;
        }
    };

    public static final BuilderWrapper<List<Double>> DOUBLE_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<Double> getBuilder() {
            return BuilderFactory.DOUBLE;
        }

        @Override
        protected Builder<List<Double>> getListBuilder() {
            return BuilderFactory.DOUBLE_LIST;
        }
    };

    public static final BuilderWrapper<List<Boolean>> BOOLEAN_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<Boolean> getBuilder() {
            return BuilderFactory.BOOLEAN;
        }

        @Override
        protected Builder<List<Boolean>> getListBuilder() {
            return BuilderFactory.BOOLEAN_LIST;
        }
    };

    public static final BuilderWrapper<List<byte[]>> BYTE_ARRAY_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<byte[]> getBuilder() {
            return BuilderFactory.BYTE_ARRAY;
        }

        @Override
        protected Builder<List<byte[]>> getListBuilder() {
            return BuilderFactory.BYTE_ARRAY_LIST;
        }
    };

    public static final BuilderWrapper<Map<String, String>> STRING_MAP = new MapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, String>> getMapBuilder() {
            return BuilderFactory.STRING_MAP;
        }
    };

    // TODO: use Iterable instead of conversation list to set
    private static final Builder<List<Tuple>> TUPLE_LIST = new Builder<>() {
        @Override
        public List<Tuple> build(Object data) {
            Set<Tuple> tupleSet = BuilderFactory.TUPLE_ZSET.build(data);
            return tupleSet == null ? null : new ArrayList<>(tupleSet);
        }

        @Override
        public String toString() {
            return "List<Tuple>";
        }
    };

    public static final BuilderWrapper<List<Tuple>> TUPLE_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<Tuple> getBuilder() {
            return BuilderFactory.TUPLE;
        }

        @Override
        protected Builder<List<Tuple>> getListBuilder() {
            return BuilderWrapperFactory.TUPLE_LIST;
        }
    };

    public static final BuilderWrapper<List<KeyedListElement>> KEYED_LIST_ELEMENT_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<KeyedListElement> getBuilder() {
            return BuilderFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final BuilderWrapper<List<KeyedZSetElement>> KEYED_ZSET_ELEMENT_RESULT = new ListBuilderWrapper<>() {
        @Override
        protected Builder<KeyedZSetElement> getBuilder() {
            return BuilderFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final BuilderWrapper<List<GeoCoordinate>> GEO_COORDINATE = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<GeoCoordinate>> getListBuilder() {
            return BuilderFactory.GEO_COORDINATE_LIST;
        }
    };

    public static final BuilderWrapper<List<GeoRadiusResponse>> GEORADIUS_RESPONSE = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<GeoRadiusResponse>> getListBuilder() {
            return BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT;
        }
    };

    public static final BuilderWrapper<List<Module>> MODULE = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<Module>> getListBuilder() {
            return BuilderFactory.MODULE_LIST;
        }
    };

    public static final BuilderWrapper<List<AccessControlUser>> ACCESS_CONTROL_USER = new ListBuilderWrapper<>() {
        @Override
        protected Builder<AccessControlUser> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final BuilderWrapper<List<AccessControlLogEntry>> ACCESS_CONTROL_LOG_ENTRY = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<AccessControlLogEntry>> getListBuilder() {
            return BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST;
        }
    };

    public static final BuilderWrapper<Map<String, CommandDocument>> COMMAND_DOCUMENT = new MapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandDocument>> getMapBuilder() {
            return BuilderFactory.COMMAND_DOCS_RESPONSE;
        }
    };

    public static final BuilderWrapper<Map<String, CommandInfo>> COMMAND_INFO = new MapBuilderWrapper<>() {
        @Override
        protected @NotNull Builder<Map<String, CommandInfo>> getMapBuilder() {
            return BuilderFactory.COMMAND_INFO_RESPONSE;
        }
    };

    public static final BuilderWrapper<List<FunctionStats>> FUNCTION_STATS = new ListBuilderWrapper<>() {
        @Override
        protected Builder<FunctionStats> getBuilder() {
            return FunctionStats.FUNCTION_STATS_BUILDER;
        }
    };

    public static final BuilderWrapper<List<LibraryInfo>> LIBRARY_INFO = new ListBuilderWrapper<LibraryInfo>() {
        @Override
        protected Builder<List<LibraryInfo>> getListBuilder() {
            return BuilderFactory.LIBRARY_LIST;
        }
    };

    // TODO: LCSMatchResult (MatchedPosition, Position)

    public static final BuilderWrapper<List<Slowlog>> SLOW_LOG = new ListBuilderWrapper<>() {
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
        protected Builder<List<Slowlog>> getListBuilder() {
            return SLOW_LOG_LIST;
        }
    };

    public static final BuilderWrapper<List<StreamEntryID>> STREAM_ENTRY_ID = new ListBuilderWrapper<>() {
        @Override
        protected Builder<StreamEntryID> getBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected Builder<List<StreamEntryID>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final BuilderWrapper<List<StreamEntry>> STREAM_ENTRY = new ListBuilderWrapper<>() {
        @Override
        protected Builder<StreamEntry> getBuilder() {
            return BuilderFactory.STREAM_ENTRY;
        }

        @Override
        protected Builder<List<StreamEntry>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final BuilderWrapper<List<Map.Entry<String, List<StreamEntry>>>> STREAM_READ_ENTRY = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<Map.Entry<String, List<StreamEntry>>>> getListBuilder() {
            return BuilderFactory.STREAM_READ_RESPONSE;
        }
    };

    public static final BuilderWrapper<List<StreamGroupInfo>> STREAM_GROUP_INFO = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<StreamGroupInfo>> getListBuilder() {
            return BuilderFactory.STREAM_GROUP_INFO_LIST;
        }
    };

    public static final BuilderWrapper<List<StreamConsumersInfo>> STREAM_CONSUMERS_INFO = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<StreamConsumersInfo>> getListBuilder() {
            return BuilderFactory.STREAM_CONSUMERS_INFO_LIST;
        }
    };

    public static final BuilderWrapper<List<StreamInfo>> STREAM_INFO = new ListBuilderWrapper<>() {
        @Override
        protected Builder<StreamInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final BuilderWrapper<List<StreamFullInfo>> STREAM_INFO_FULL = new ListBuilderWrapper<>() {
        @Override
        protected Builder<StreamFullInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO_FULL;
        }
    };

    public static final BuilderWrapper<List<StreamPendingEntry>> STREAM_PENDING_ENTRY = new ListBuilderWrapper<>() {
        @Override
        protected Builder<List<StreamPendingEntry>> getListBuilder() {
            return BuilderFactory.STREAM_PENDING_ENTRY_LIST;
        }
    };

    public static final BuilderWrapper<List<StreamPendingSummary>> STREAM_PENDING_SUMMARY = new ListBuilderWrapper<>() {
        @Override
        protected Builder<StreamPendingSummary> getBuilder() {
            return BuilderFactory.STREAM_PENDING_SUMMARY;
        }
    };

    public static final BuilderWrapper<List<ScanResult<String>>> STRING_SCAN_RESULT = new ScanResultBuilder<>() {
        @Override
        protected @NotNull Builder<List<String>> getResultsBuilder() {
            return BuilderFactory.STRING_LIST;
        }
    };

    public static final BuilderWrapper<List<ScanResult<Tuple>>> TUPLE_SCAN_RESULT = new ScanResultBuilder<>() {
        @Override
        protected @NotNull Builder<List<Tuple>> getResultsBuilder() {
            return BuilderWrapperFactory.TUPLE_LIST;
        }
    };

    private static abstract class ScanResultBuilder<T> extends ListBuilderWrapper<ScanResult<T>> {

        abstract protected @NotNull Builder<List<T>> getResultsBuilder();

        protected @Nullable Builder<ScanResult<T>> getBuilder() {
            return new Builder<>() {
                @Override
                public ScanResult<T> build(Object data) {
                    if (data == null) return null;
                    List<?> l = (List<?>) data;
                    String cursor = BuilderFactory.STRING.build(l.get(0));
                    List<T> results = getResultsBuilder().build(l.get(1));
                    return new ScanResult<>(cursor, results);
                }
            };
        }

        @Override
        public String toString() {
            return getResultsBuilder().toString().replaceFirst("List", "ScanResult");
        }
    }


    private static abstract class ListBuilderWrapper<T> extends BuilderWrapper<List<T>> {

        protected @Nullable Builder<T> getBuilder() {
            return null;
        }

        protected @Nullable Builder<List<T>> getListBuilder() {
            return null;
        }

        @Override
        public @NotNull List<T> build(@Nullable Object data) {
            Builder<List<T>> listBuilder = getListBuilder();
            Builder<T> builder = getBuilder();
            if (data != null) {
                if (listBuilder != null && data instanceof List) return listBuilder.build(data);
                if (builder != null) return Collections.singletonList(builder.build(data));
            }
            return builder == null ? Collections.emptyList() : Collections.singletonList(null);
        }

        @Override
        public String toString() {
            Builder<List<T>> listBuilder = getListBuilder();
            if (listBuilder != null) return listBuilder.toString();
            return String.format("List<%s>", getBuilder());
        }
    }

    private static abstract class MapBuilderWrapper<T> extends BuilderWrapper<Map<String, T>> {

        abstract protected @NotNull Builder<Map<String, T>> getMapBuilder();

        @Override
        public @NotNull Map<String, T> build(Object data) {
            return data != null ? getMapBuilder().build(data) : Collections.emptyMap();
        }

        @Override
        public String toString() {
            return getMapBuilder().toString();
        }
    }
}
