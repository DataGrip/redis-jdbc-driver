package jdbc.client.result.parser.converter;

import jdbc.client.query.structures.Params;
import jdbc.client.result.structures.ObjectType;
import jdbc.client.result.structures.SimpleType;
import jdbc.types.RedisColumnTypeHelper;
import jdbc.utils.Utils;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.SearchProtocol.SearchKeyword;
import redis.clients.jedis.search.SearchResult;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.timeseries.TSElement;
import redis.clients.jedis.timeseries.TSKeyValue;
import redis.clients.jedis.timeseries.TSKeyedElements;
import redis.clients.jedis.timeseries.TimeSeriesProtocol.TimeSeriesKeyword;
import redis.clients.jedis.util.KeyValue;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TypeFactory {

    private TypeFactory() {
    }


    /* --------------------------------------------- Common --------------------------------------------- */

    public static final SimpleType<Object> OBJECT = new SimpleType<>(RedisColumnTypeHelper.OBJECT);

    public static final SimpleType<String> STRING = new SimpleType<>(RedisColumnTypeHelper.STRING);

    public static final SimpleType<Integer> INT = new SimpleType<>(RedisColumnTypeHelper.LONG); // use long everywhere

    public static final SimpleType<Long> LONG = new SimpleType<>(RedisColumnTypeHelper.LONG);

    public static final SimpleType<Double> DOUBLE = new SimpleType<>(RedisColumnTypeHelper.DOUBLE);

    public static final SimpleType<Boolean> BOOLEAN = new SimpleType<>(RedisColumnTypeHelper.BOOLEAN);

    public static final SimpleType<byte[]> BYTE_ARRAY = new SimpleType<>(RedisColumnTypeHelper.BINARY);

    public static final SimpleType<List<?>> LIST = new ListSimpleType<>();

    public static class ListSimpleType<T extends List<?>> extends SimpleType<T> {
        public ListSimpleType() {
            super(RedisColumnTypeHelper.ARRAY);
        }
    }

    public static final SimpleType<Map<String, ?>> MAP = new MapSimpleType<>();

    public static class MapSimpleType<T extends Map<String, ?>> extends SimpleType<T> {
        public MapSimpleType() {
            super(RedisColumnTypeHelper.MAP);
        }
    }


    /* --------------------------------------------- Native --------------------------------------------- */

    public static final ObjectType<KeyedListElement> KEYED_STRING = new ObjectType<>() {{
        add("key", STRING, AbstractMap.SimpleImmutableEntry::getKey);
        add("value", STRING, KeyedListElement::getElement);
    }};

    public static final ObjectType<Tuple> TUPLE = new ObjectType<>() {{
        add("value", STRING, Tuple::getElement);
        add("score", DOUBLE, Tuple::getScore);
    }};

    public static final ObjectType<KeyedZSetElement> KEYED_TUPLE = new ObjectType<>() {{
        add("key", STRING, KeyedZSetElement::getKey);
        add("value", STRING, Tuple::getElement);
        add("score", DOUBLE, Tuple::getScore);
    }};


    public static final ObjectType<KeyValue<String, List<String>>> KEYED_STRING_LIST = new ObjectType<>() {{
        add("key", STRING, KeyValue::getKey);
        add("values", LIST, KeyValue::getValue, ConverterFactory.STRING::convertList);
    }};

    public static final ObjectType<KeyValue<String, List<Tuple>>> KEYED_TUPLE_LIST = new ObjectType<>() {{
        add("key", STRING, KeyValue::getKey);
        add("elements", LIST, KeyValue::getValue, ConverterFactory.TUPLE::convertList);
    }};


    public static final ObjectType<ScanResult<String>> STRING_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected BiFunction<List<String>, Params, List<?>> getResultsConverter() {
            return ConverterFactory.STRING::convertList;
        }
    };

    public static final ObjectType<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected BiFunction<List<Tuple>, Params, List<?>> getResultsConverter() {
            return ConverterFactory.TUPLE::convertList;
        }
    };

    public static final ObjectType<Map.Entry<String, String>> ENTRY = new ObjectType<>() {{
        add("field", STRING, Map.Entry::getKey);
        add("value", STRING, Map.Entry::getValue);
    }};

    public static final ObjectType<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected BiFunction<List<Map.Entry<String, String>>, Params, List<?>> getResultsConverter() {
            return ConverterFactory.ENTRY::convertList;
        }
    };

    private static abstract class ScanResultType<T> extends ObjectType<ScanResult<T>> {
        protected ScanResultType() {
            add("cursor", STRING, ScanResult::getCursor);
            add("results", LIST, ScanResult::getResult, getResultsConverter());
        }

        protected abstract BiFunction<List<T>, Params, List<?>> getResultsConverter();
    }


    public static final ObjectType<StreamEntry> STREAM_ENTRY = new ObjectType<>() {{
        add("id", STRING, StreamEntry::getID, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("fields", MAP, StreamEntry::getFields);
    }};

    public static final ObjectType<Map.Entry<StreamEntryID, List<StreamEntry>>> STREAM_AUTO_CLAIM_RESPONSE = new ObjectType<>() {{
        add("cursor-id", STRING, Map.Entry::getKey, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("entries", LIST, Map.Entry::getValue, ConverterFactory.STREAM_ENTRY::convertList);
    }};

    public static final ObjectType<Map.Entry<StreamEntryID, List<StreamEntryID>>> STREAM_AUTO_CLAIM_ID_RESPONSE = new ObjectType<>() {{
        add("cursor-id", STRING, Map.Entry::getKey, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("ids", LIST, Map.Entry::getValue, ConverterFactory.STREAM_ENTRY_ID::convertList);
    }};

    public static final ObjectType<Map.Entry<String, List<StreamEntry>>> STREAM_READ_RESPONSE = new ObjectType<>() {{
        add("key", STRING, Map.Entry::getKey);
        add("entries", LIST, Map.Entry::getValue, ConverterFactory.STREAM_ENTRY::convertList);
    }};

    public static final ObjectType<StreamConsumersInfo> STREAM_CONSUMER_INFO = new ObjectType<>() {{
        add("name", STRING, StreamConsumersInfo::getName);
        add("idle", LONG, StreamConsumersInfo::getIdle);
        add("pending", LONG, StreamConsumersInfo::getPending);
    }};

    public static final ObjectType<StreamConsumerFullInfo> STREAM_CONSUMER_INFO_FULL = new ObjectType<>() {{
        add("name", STRING, StreamConsumerFullInfo::getName);
        add("seen-time", LONG, StreamConsumerFullInfo::getSeenTime);
        add("pel-count", LONG, StreamConsumerFullInfo::getPelCount);
        add("pending", LIST, StreamConsumerFullInfo::getPending);
    }};

    public static final ObjectType<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectType<>() {{
        add("name", STRING, StreamGroupInfo::getName);
        add("consumers", LONG, StreamGroupInfo::getConsumers);
        add("pending", LONG, StreamGroupInfo::getPending);
        add("last-delivered-id", STRING, StreamGroupInfo::getLastDeliveredId, ConverterFactory.STREAM_ENTRY_ID::convert);
    }};

    public static final ObjectType<StreamGroupFullInfo> STREAM_GROUP_INFO_FULL = new ObjectType<>() {{
        add("name", STRING, StreamGroupFullInfo::getName);
        add("consumers", LIST, StreamGroupFullInfo::getConsumers, ConverterFactory.STREAM_CONSUMER_INFO_FULL::convertList);
        add("pending", LIST, StreamGroupFullInfo::getPending);
        add("pel-count", LONG, StreamGroupFullInfo::getPelCount);
        add("last-delivered-id", STRING, StreamGroupFullInfo::getLastDeliveredId, ConverterFactory.STREAM_ENTRY_ID::convert);
    }};

    public static final ObjectType<StreamInfo> STREAM_INFO = new ObjectType<>() {{
        add("length", LONG, StreamInfo::getLength);
        add("radix-tree-keys", LONG, StreamInfo::getRadixTreeKeys);
        add("radix-tree-nodes", LONG, StreamInfo::getRadixTreeNodes);
        add("groups", LONG, StreamInfo::getGroups);
        add("last-generated-id", STRING, StreamInfo::getLastGeneratedId, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("first-entry", MAP, StreamInfo::getFirstEntry, ConverterFactory.STREAM_ENTRY::convert);
        add("last-entry", MAP, StreamInfo::getLastEntry, ConverterFactory.STREAM_ENTRY::convert);
    }};

    public static final ObjectType<StreamFullInfo> STREAM_INFO_FULL = new ObjectType<>() {{
        add("length", LONG, StreamFullInfo::getLength);
        add("radix-tree-keys", LONG, StreamFullInfo::getRadixTreeKeys);
        add("radix-tree-nodes", LONG, StreamFullInfo::getRadixTreeNodes);
        add("groups", LIST, StreamFullInfo::getGroups, ConverterFactory.STREAM_GROUP_INFO_FULL::convertList);
        add("last-generated-id", STRING, StreamFullInfo::getLastGeneratedId, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("entries", LIST, StreamFullInfo::getEntries, ConverterFactory.STREAM_ENTRY::convertList);
    }};

    public static final ObjectType<StreamPendingEntry> STREAM_PENDING_ENTRY = new ObjectType<>() {{
        add("id", STRING, StreamPendingEntry::getID, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("consumer-name", STRING, StreamPendingEntry::getConsumerName);
        add("idle-time", LONG, StreamPendingEntry::getIdleTime);
        add("delivered-times", LONG, StreamPendingEntry::getDeliveredTimes);
    }};

    public static final ObjectType<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ObjectType<>() {{
        add("total", LONG, StreamPendingSummary::getTotal);
        add("min-id", STRING, StreamPendingSummary::getMinId, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("max-id", STRING, StreamPendingSummary::getMaxId, ConverterFactory.STREAM_ENTRY_ID::convert);
        add("consumer-message-count", MAP, StreamPendingSummary::getConsumerMessageCount);
    }};


    public static final ObjectType<GeoCoordinate> GEO_COORDINATE = new ObjectType<>() {{
        add("longitude", DOUBLE, GeoCoordinate::getLongitude);
        add("latitude", DOUBLE, GeoCoordinate::getLatitude);
    }};

    public static final ObjectType<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectType<>() {{
        add("member", STRING, GeoRadiusResponse::getMemberByString);
        add("distance", DOUBLE, GeoRadiusResponse::getDistance, Utils.contains(Keyword.WITHDIST));
        add("coordinate", MAP, GeoRadiusResponse::getCoordinate, ConverterFactory.GEO_COORDINATE::convert, Utils.contains(Keyword.WITHCOORD));
        add("raw-score", LONG, GeoRadiusResponse::getRawScore, Utils.contains(Keyword.WITHHASH));
    }};

    public static final ObjectType<Module> MODULE = new ObjectType<>() {{
        add("name", STRING, Module::getName);
        add("version", INT, Module::getVersion);
    }};

    public static final ObjectType<AccessControlUser> ACCESS_CONTROL_USER = new ObjectType<>() {{
        add("flags", LIST, AccessControlUser::getFlags);
        add("keys", LIST, AccessControlUser::getKeys);
        add("passwords", LIST, AccessControlUser::getPassword);
        add("commands", STRING, AccessControlUser::getCommands);
    }};

    public static final ObjectType<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectType<>() {{
        add("count", LONG, AccessControlLogEntry::getCount);
        add("reason", STRING, AccessControlLogEntry::getReason);
        add("context", STRING, AccessControlLogEntry::getContext);
        add("object", STRING, AccessControlLogEntry::getObject);
        add("username", STRING, AccessControlLogEntry::getUsername);
        add("age-seconds", STRING, AccessControlLogEntry::getAgeSeconds);
        add("client-info", MAP, AccessControlLogEntry::getClientInfo);
    }};

    // TODO: CommandDocument: arguments?
    public static final ObjectType<CommandDocument> COMMAND_DOCUMENT = new ObjectType<>("command-name") {{
        add("summary", STRING, CommandDocument::getSummary);
        add("since", STRING, CommandDocument::getSince);
        add("group", STRING, CommandDocument::getGroup);
        add("complexity", STRING, CommandDocument::getComplexity);
        add("history", LIST, CommandDocument::getHistory);
    }};

    public static final ObjectType<CommandInfo> COMMAND_INFO = new ObjectType<>("command-name") {{
        add("arity", LONG, CommandInfo::getArity);
        add("flags", LIST, CommandInfo::getFlags);
        add("first-key", LONG, CommandInfo::getFirstKey);
        add("last-key", LONG, CommandInfo::getLastKey);
        add("step", LONG, CommandInfo::getStep);
        add("acl-categories", LIST, CommandInfo::getAclCategories);
        add("tips", LIST, CommandInfo::getTips);
        add("subcommands", LIST, CommandInfo::getSubcommands);
    }};

    public static final ObjectType<FunctionStats> FUNCTION_STATS = new ObjectType<>() {{
        add("running-script", MAP, FunctionStats::getRunningScript);
        add("engines", MAP, FunctionStats::getEngines);
    }};

    public static final ObjectType<LibraryInfo> LIBRARY_INFO = new ObjectType<>() {{
        add("library-name", STRING, LibraryInfo::getLibraryName);
        add("engine", STRING, LibraryInfo::getEngine);
        add("functions", LIST, LibraryInfo::getFunctions);
        add("library-code", STRING, LibraryInfo::getLibraryCode, Utils.contains(Keyword.WITHCODE));
    }};

    public static final ObjectType<Slowlog> SLOW_LOG = new ObjectType<>() {{
        add("id", LONG, Slowlog::getId);
        add("timestamp", LONG, Slowlog::getTimeStamp);
        add("execution-time", LONG, Slowlog::getExecutionTime);
        add("args", LIST, Slowlog::getArgs);
        add("client-ip-port", STRING, Slowlog::getClientIpPort, ConverterFactory.HOST_AND_PORT::convert);
        add("client-name", STRING, Slowlog::getClientName);
    }};

    public static final ObjectType<LCSMatchResult.Position> LCS_POSITION = new ObjectType<>() {{
        add("start", LONG, LCSMatchResult.Position::getStart);
        add("end", LONG, LCSMatchResult.Position::getEnd);
    }};

    public static final ObjectType<LCSMatchResult.MatchedPosition> LCS_MATCHED_POSITION = new ObjectType<>() {{
        add("a", MAP, LCSMatchResult.MatchedPosition::getA, ConverterFactory.LCS_POSITION::convert);
        add("b", MAP, LCSMatchResult.MatchedPosition::getB, ConverterFactory.LCS_POSITION::convert);
        add("match-len", LONG, LCSMatchResult.MatchedPosition::getMatchLen, Utils.contains(Keyword.WITHMATCHLEN));
    }};

    public static final ObjectType<LCSMatchResult> LCS_MATCH_RESULT = new ObjectType<>() {{
        add("match-string", STRING, LCSMatchResult::getMatchString, Utils.contains(Keyword.LEN, Keyword.IDX).negate());
        add("matches", LIST, LCSMatchResult::getMatches, ConverterFactory.LCS_MATCHED_POSITION::convertList, Utils.contains(Keyword.IDX));
        add("len", LONG, LCSMatchResult::getLen, Utils.contains(Keyword.LEN, Keyword.IDX));
    }};

    public static final ObjectType<KeyValue<Long, Long>> WAITAOF_RESPONSE = new ObjectType<>() {{
        add("local", LONG, KeyValue::getKey);
        add("value", LONG, KeyValue::getValue);
    }};

    /* --------------------------------------------- RedisJSON --------------------------------------------- */


    /* --------------------------------------------- RediSearch --------------------------------------------- */

    public static final ObjectType<AggregationResult> AGGREGATION_RESULT = new ObjectType<>() {{
        add("total-results", LONG, AggregationResult::getTotalResults);
        add("results", LIST, AggregationResult::getResults);
        add("cursor-id", LONG, AggregationResult::getCursorId, Utils.contains(SearchKeyword.WITHCURSOR));
    }};

    public static final ObjectType<Map.Entry<AggregationResult, Map<String, Object>>> AGGREGATION_PROFILE_RESPONSE = new ObjectType<>() {{
        add("aggregation-result", MAP, Map.Entry::getKey, ConverterFactory.AGGREGATION_RESULT::convert);
        add("profile", MAP, Map.Entry::getValue);
    }};

    public static final ObjectType<Document> SEARCH_DOCUMENT = new ObjectType<>() {{
        add("id", STRING, Document::getId);
        add("score", DOUBLE, Document::getScore);
        add("properties", MAP, d -> StreamSupport
                .stream(d.getProperties().spliterator(), false)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }};

    public static final ObjectType<SearchResult> SEARCH_RESULT = new ObjectType<>() {{
        add("total-results", LONG, SearchResult::getTotalResults);
        add("documents", LIST, SearchResult::getDocuments, ConverterFactory.SEARCH_DOCUMENT::convertList);
    }};

    public static final ObjectType<Map.Entry<SearchResult, Map<String, Object>>> SEARCH_PROFILE_RESPONSE = new ObjectType<>() {{
        add("search-result", MAP, Map.Entry::getKey, ConverterFactory.SEARCH_RESULT::convert);
        add("profile", MAP, Map.Entry::getValue);
    }};


    /* --------------------------------------------- RedisBloom --------------------------------------------- */

    public static final ObjectType<Map.Entry<Long, byte[]>> BLOOM_SCANDUMP_RESPONSE = new ObjectType<>() {{
        add("iterator", LONG, Map.Entry::getKey);
        add("data", BYTE_ARRAY, Map.Entry::getValue);
    }};


    /* --------------------------------------------- RedisTimeSeries --------------------------------------------- */

    public static final ObjectType<TSElement> TIMESERIES_ELEMENT = new ObjectType<>() {{
        add("timestamp", LONG, TSElement::getTimestamp);
        add("value", DOUBLE, TSElement::getValue);
    }};

    public static final ObjectType<TSKeyValue<TSElement>> TIMESERIES_MGET_RESPONSE = new ObjectType<>() {{
        add("key", STRING, TSKeyValue::getKey);
        add("labels", MAP, TSKeyValue::getLabels, Utils.contains(TimeSeriesKeyword.WITHLABELS, TimeSeriesKeyword.SELECTED_LABELS));
        add("element", MAP, TSKeyValue::getValue, ConverterFactory.TIMESERIES_ELEMENT::convert);
    }};

    public static final ObjectType<TSKeyedElements> TIMESERIES_MRANGE_RESPONSE = new ObjectType<>() {{
        add("key", STRING, TSKeyedElements::getKey);
        add("elements", LIST, TSKeyedElements::getValue, ConverterFactory.TIMESERIES_ELEMENT::convertList);
    }};


    /* ------------------------------------------------------------------------------------------ */

}
