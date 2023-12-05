package jdbc.client.result.parser.encoder;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.json.DefaultGsonObjectMapper;
import redis.clients.jedis.json.JsonObjectMapper;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.SearchBuilderFactory;
import redis.clients.jedis.search.SearchProtocol.SearchKeyword;
import redis.clients.jedis.search.SearchResult;
import redis.clients.jedis.search.SearchResult.SearchResultBuilder;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.timeseries.*;
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
        protected @NotNull Builder<Object> getBuilder(@NotNull Params params) {
            return BuilderFactory.ENCODED_OBJECT;
        }

        @Override
        protected @NotNull Builder<List<Object>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.ENCODED_OBJECT_LIST;
        }
    };

    public static final ListEncoder<String> STRING = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<String> getBuilder(@NotNull Params params) {
            return BuilderFactory.STRING;
        }

        @Override
        protected @NotNull Builder<List<String>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.STRING_LIST;
        }
    };

    public static final ListEncoder<Long> LONG = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Long> getBuilder(@NotNull Params params) {
            return BuilderFactory.LONG;
        }

        @Override
        protected @NotNull Builder<List<Long>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.LONG_LIST;
        }
    };

    public static final ListEncoder<Double> DOUBLE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Double> getBuilder(@NotNull Params params) {
            return BuilderFactory.DOUBLE;
        }

        @Override
        protected @NotNull Builder<List<Double>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.DOUBLE_LIST;
        }
    };

    public static final ListEncoder<Boolean> BOOLEAN = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Boolean> getBuilder(@NotNull Params params) {
            return BuilderFactory.BOOLEAN;
        }

        @Override
        protected @NotNull Builder<List<Boolean>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.BOOLEAN_WITH_ERROR_LIST;
        }
    };

    public static final ListEncoder<byte[]> BYTE_ARRAY = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<byte[]> getBuilder(@NotNull Params params) {
            return BuilderFactory.BYTE_ARRAY;
        }

        @Override
        protected @NotNull Builder<List<byte[]>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.BYTE_ARRAY_LIST;
        }
    };


    public static final ListEncoder<List<Object>> OBJECT_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<List<Object>> getBuilder(@NotNull Params params) {
            return BuilderFactory.ENCODED_OBJECT_LIST;
        }

        @Override
        protected @NotNull Builder<List<List<Object>>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.ENCODED_OBJECT_LIST_LIST;
        }
    };

    public static final ListEncoder<List<String>> STRING_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<List<String>> getBuilder(@NotNull Params params) {
            return BuilderFactory.STRING_LIST;
        }

        @Override
        protected @NotNull Builder<List<List<String>>> getListBuilder(@NotNull Params params) {
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


    /* --------------------------------------------- Native --------------------------------------------- */

    public static final ListEncoder<KeyedListElement> KEYED_STRING = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyedListElement> getBuilder(@NotNull Params params) {
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
        protected @NotNull Builder<KeyedZSetElement> getBuilder(@NotNull Params params) {
            return BuilderFactory.KEYED_ZSET_ELEMENT;
        }
    };


    public static final ListEncoder<KeyValue<String, List<String>>> KEYED_STRING_LIST = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyValue<String, List<String>>> getBuilder(@NotNull Params params) {
            return BuilderFactory.KEYED_STRING_LIST;
        }

        @Override
        protected @NotNull Builder<List<KeyValue<String, List<String>>>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.KEYED_STRING_LIST_LIST;
        }
    };

    public static final ListEncoder<KeyValue<String, List<Tuple>>> KEYED_TUPLE_LIST = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<KeyValue<String, List<Tuple>>> getBuilder(@NotNull Params params) {
            return BuilderFactory.KEYED_TUPLE_LIST;
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
        protected @NotNull Builder<AccessControlUser> getBuilder(@NotNull Params params) {
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
        protected @NotNull Builder<FunctionStats> getBuilder(@NotNull Params params) {
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
        };

        @Override
        protected @NotNull Builder<List<Slowlog>> getListBuilder() {
            return SLOW_LOG_LIST;
        }
    };

    public static final ListEncoder<LCSMatchResult> LCS_MATCH_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<LCSMatchResult> getBuilder(@NotNull Params params) {
            return BuilderFactory.STR_ALGO_LCS_RESULT_BUILDER;
        }
    };

    public static final MapEncoder<Long> PUBSUB_NUMSUB_RESPONSE = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, Long>> getMapBuilder() {
            return BuilderFactory.PUBSUB_NUMSUB_MAP;
        }
    };


    public static final ListEncoder<StreamEntryID> STREAM_ENTRY_ID = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamEntryID> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull Builder<List<StreamEntryID>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final ListEncoder<StreamEntry> STREAM_ENTRY = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamEntry> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull Builder<List<StreamEntry>> getListBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final ListEncoder<Map.Entry<StreamEntryID, List<StreamEntry>>> STREAM_AUTO_CLAIM_RESPONSE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Map.Entry<StreamEntryID, List<StreamEntry>>> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_AUTO_CLAIM_RESPONSE;
        }
    };

    public static final ListEncoder<Map.Entry<StreamEntryID, List<StreamEntryID>>> STREAM_AUTO_CLAIM_ID_RESPONSE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Map.Entry<StreamEntryID, List<StreamEntryID>>> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_AUTO_CLAIM_ID_RESPONSE;
        }
    };

    public static final ListEncoder<Map.Entry<String, List<StreamEntry>>> STREAM_READ_RESPONSE = new SimpleListEncoder<>() {
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
        protected @NotNull Builder<StreamInfo> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final ListEncoder<StreamFullInfo> STREAM_INFO_FULL = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<StreamFullInfo> getBuilder(@NotNull Params params) {
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
        protected @NotNull Builder<StreamPendingSummary> getBuilder(@NotNull Params params) {
            return BuilderFactory.STREAM_PENDING_SUMMARY;
        }
    };


    public static final ListEncoder<ScanResult<String>> STRING_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<String>> getBuilder(@NotNull Params params) {
            return BuilderFactory.SCAN_RESPONSE;
        }
    };

    public static final ListEncoder<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<Tuple>> getBuilder(@NotNull Params params) {
            return BuilderFactory.ZSCAN_RESPONSE;
        }
    };

    public static final ListEncoder<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<ScanResult<Map.Entry<String, String>>> getBuilder(@NotNull Params params) {
            return BuilderFactory.HSCAN_RESPONSE;
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
        protected @NotNull Builder<Object> getBuilder(@NotNull Params params) {
            return JSON_OBJECT;
        }

        @Override
        protected @NotNull Builder<List<Object>> getListBuilder(@NotNull Params params) {
            return JSON_OBJECT_LIST;
        }
    };


    /* --------------------------------------------- RediSearch --------------------------------------------- */

    private static class SearchProfileResponseBuilder<T> extends Builder<Map.Entry<T, Map<String, Object>>> {

        private final Builder<T> replyBuilder;

        public SearchProfileResponseBuilder(@NotNull Builder<T> replyBuilder) {
            this.replyBuilder = replyBuilder;
        }

        @Override
        public Map.Entry<T, Map<String, Object>> build(Object data) {
            List<?> list = (List<?>) data;
            return KeyValue.of(replyBuilder.build(list.get(0)),
                    SearchBuilderFactory.SEARCH_PROFILE_PROFILE.build(list.get(1)));
        }
    }

    private static @NotNull Builder<AggregationResult> getAggregationResultBuilder(@NotNull Params params) {
        boolean withCursor = params.contains(SearchKeyword.WITHCURSOR);
        if (withCursor) return SearchBuilderFactory.SEARCH_AGGREGATION_RESULT_WITH_CURSOR;
        return SearchBuilderFactory.SEARCH_AGGREGATION_RESULT;
    }

    public static final ListEncoder<AggregationResult> AGGREGATION_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<AggregationResult> getBuilder(@NotNull Params params) {
            return getAggregationResultBuilder(params);
        }
    };

    public static final ListEncoder<Map.Entry<AggregationResult, Map<String, Object>>> AGGREGATION_PROFILE_RESPONSE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Map.Entry<AggregationResult, Map<String, Object>>> getBuilder(@NotNull Params params) {
            return new SearchProfileResponseBuilder<>(getAggregationResultBuilder(params));
        }
    };

    private static @NotNull Builder<SearchResult> getSearchResultBuilder(@NotNull Params params) {
        boolean hasContent = !params.contains(SearchKeyword.NOCONTENT);
        boolean hasScores = params.contains(SearchKeyword.WITHSCORES);
        boolean hasPayloads = params.contains(SearchKeyword.WITHPAYLOADS);
        return new SearchResultBuilder(hasContent, hasScores, hasPayloads, true);
    }

    public static final ListEncoder<SearchResult> SEARCH_RESULT = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<SearchResult> getBuilder(@NotNull Params params) {
            return getSearchResultBuilder(params);
        }
    };

    public static final ListEncoder<Map.Entry<SearchResult, Map<String, Object>>> SEARCH_PROFILE_RESPONSE = new ElementListEncoder<>() {
        @Override
        protected @NotNull Builder<Map.Entry<SearchResult, Map<String, Object>>> getBuilder(@NotNull Params params) {
            return new SearchProfileResponseBuilder<>(getSearchResultBuilder(params));
        }
    };


    public static final MapEncoder<Map<String, Double>> SEARCH_SPELLCHECK_RESPONSE = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, Map<String, Double>>> getMapBuilder() {
            return SearchBuilderFactory.SEARCH_SPELLCHECK_RESPONSE;
        }
    };

    public static final MapEncoder<List<String>> SEARCH_SYNONYM_GROUPS = new SimpleMapEncoder<>() {
        @Override
        protected @NotNull Builder<Map<String, List<String>>> getMapBuilder() {
            return SearchBuilderFactory.SEARCH_SYNONYM_GROUPS;
        }
    };


    /* --------------------------------------------- RedisBloom --------------------------------------------- */

    public static final ListEncoder<Map.Entry<Long, byte[]>> BLOOM_SCANDUMP_RESPONSE = new ElementListEncoder<>() {

        private final Builder<Map.Entry<Long, byte[]>> BLOOM_SCANDUMP_RESPONSE = new Builder<>() {
            @Override
            public Map.Entry<Long, byte[]> build(Object data) {
                List<?> list = (List<?>) data;
                return new KeyValue<>(BuilderFactory.LONG.build(list.get(0)), BuilderFactory.BINARY.build(list.get(1)));
            }
        };

        @Override
        protected @NotNull Builder<Map.Entry<Long, byte[]>> getBuilder(@NotNull Params params) {
            return BLOOM_SCANDUMP_RESPONSE;
        }
    };


    /* --------------------------------------------- RedisTimeSeries --------------------------------------------- */

    public static final ListEncoder<TSElement> TIMESERIES_ELEMENT = new ListElementListEncoder<>() {
        @Override
        protected @NotNull Builder<TSElement> getBuilder(@NotNull Params params) {
            return TimeSeriesBuilderFactory.TIMESERIES_ELEMENT;
        }

        @Override
        protected @NotNull Builder<List<TSElement>> getListBuilder(@NotNull Params params) {
            return TimeSeriesBuilderFactory.TIMESERIES_ELEMENT_LIST;
        }
    };

    public static final ListEncoder<TSKeyValue<TSElement>> TIMESERIES_MGET_RESPONSE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<TSKeyValue<TSElement>>> getListBuilder() {
            return TimeSeriesBuilderFactory.TIMESERIES_MGET_RESPONSE;
        }
    };

    public static final ListEncoder<TSKeyedElements> TIMESERIES_MRANGE_RESPONSE = new SimpleListEncoder<>() {
        @Override
        protected @NotNull Builder<List<TSKeyedElements>> getListBuilder() {
            return TimeSeriesBuilderFactory.TIMESERIES_MRANGE_RESPONSE;
        }
    };


    public static final MapEncoder<Object> TIMESERIES_INFO = new SimpleMapEncoder<>() {

        private final Builder<Map<String, Object>> TS_INFO = new Builder<>() {
            @Override
            public Map<String, Object> build(Object data) {
                return TSInfo.TIMESERIES_INFO.build(data).getProperties();
            }
        };

        @Override
        protected @NotNull Builder<Map<String, Object>> getMapBuilder() {
            return TS_INFO;
        }
    };


    /* ------------------------------------------------------------------------------------------ */

    private abstract static class SimpleListEncoder<T> extends ListEncoder<T> {

        protected abstract @NotNull Builder<List<T>> getListBuilder();

        @Override
        public final @NotNull List<T> encode(@Nullable Object data, @NotNull Params params) {
            if (data == null) return Collections.emptyList();
            return getListBuilder().build(data);
        }
    }

    private abstract static class SimpleMapEncoder<T> extends MapEncoder<T> {

        protected abstract @NotNull Builder<Map<String, T>> getMapBuilder();

        @Override
        public final @NotNull Map<String, T> encode(@Nullable Object data, @NotNull Params params) {
            if (data == null) return Collections.emptyMap();
            return getMapBuilder().build(data);
        }
    }

    private static abstract class ElementListEncoder<T> extends ListEncoder<T> {

        protected abstract @NotNull Builder<T> getBuilder(@NotNull Params params);

        protected @Nullable Builder<List<T>> getListBuilder(@NotNull Params params) {
            return null;
        }

        protected boolean isElementList(@NotNull Object data, @NotNull Params params) {
            return getListBuilder(params) != null && data instanceof List;
        }

        @Override
        public @NotNull List<T> encode(@Nullable Object data, @NotNull Params params) {
            if (data == null) return Collections.singletonList(null);
            if (isElementList(data, params)) return Objects.requireNonNull(getListBuilder(params)).build(data);
            return Collections.singletonList(getBuilder(params).build(data));
        }
    }

    private static abstract class ListElementListEncoder<T> extends ElementListEncoder<T> {
        @Override
        protected boolean isElementList(@NotNull Object data, @NotNull Params params) {
            if (!super.isElementList(data, params)) return false;
            List<?> dataList = (List<?>) data;
            return dataList.isEmpty() || dataList.get(0) instanceof List;
        }
    }
}
