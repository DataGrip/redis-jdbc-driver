package jdbc.client.helpers.result.parser.converter.type;

import jdbc.client.helpers.result.parser.converter.ConverterFactory;
import jdbc.client.structures.result.ObjectType;
import jdbc.client.structures.result.SimpleType;
import jdbc.types.RedisColumnTypeHelper;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.util.KeyValue;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static jdbc.Utils.param;

public class TypeFactory {

    private TypeFactory() {
    }


    // TODO: think about it
    public static final SimpleType<Object> OBJECT = new SimpleType<>(RedisColumnTypeHelper.STRING); // object.toString()

    public static final SimpleType<String> STRING = new SimpleType<>(RedisColumnTypeHelper.STRING);

    public static final SimpleType<Integer> INT = new SimpleType<>(RedisColumnTypeHelper.LONG); // use long everywhere

    public static final SimpleType<Long> LONG = new SimpleType<>(RedisColumnTypeHelper.LONG);

    public static final SimpleType<Double> DOUBLE = new SimpleType<>(RedisColumnTypeHelper.DOUBLE);

    public static final SimpleType<Boolean> BOOLEAN = new SimpleType<>(RedisColumnTypeHelper.BOOLEAN);

    public static final SimpleType<byte[]> BYTE_ARRAY = new SimpleType<>(RedisColumnTypeHelper.BINARY);

    public static final SimpleType<List<?>> LIST = new SimpleType<>(RedisColumnTypeHelper.ARRAY);

    public static final SimpleType<Map<String, ?>> MAP = new SimpleType<>(RedisColumnTypeHelper.MAP);


    public static final ObjectType<Tuple> TUPLE = new ObjectType<>() {{
        add("value", STRING, Tuple::getElement);
        add("score", DOUBLE, Tuple::getScore);
    }};

    public static final ObjectType<KeyedZSetElement> KEYED_ZSET_ELEMENT = new ObjectType<>() {{
        add("key", STRING, KeyedZSetElement::getKey);
        add("value", STRING, Tuple::getElement);
        add("score", DOUBLE, Tuple::getScore);
    }};

    public static final ObjectType<GeoCoordinate> GEO_COORDINATE = new ObjectType<>() {{
        add("longitude", DOUBLE, GeoCoordinate::getLongitude);
        add("latitude", DOUBLE, GeoCoordinate::getLatitude);
    }};

    public static final ObjectType<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectType<>() {{
        add("member", STRING, GeoRadiusResponse::getMemberByString);
        add("distance", DOUBLE, GeoRadiusResponse::getDistance, param(Protocol.Keyword.WITHDIST));
        add("coordinate", MAP, GeoRadiusResponse::getCoordinate, ConverterFactory.GEO_COORDINATE::convert, param(Protocol.Keyword.WITHCOORD));
        add("raw-score", LONG, GeoRadiusResponse::getRawScore, param(Protocol.Keyword.WITHHASH));
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
        add("firstKey", LONG, CommandInfo::getFirstKey);
        add("lastKey", LONG, CommandInfo::getLastKey);
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
        add("library-code", STRING, LibraryInfo::getLibraryCode, param(Protocol.Keyword.WITHCODE));
    }};

    public static final ObjectType<Slowlog> SLOW_LOG = new ObjectType<>() {{
        add("id", LONG, Slowlog::getId);
        add("timestamp", LONG, Slowlog::getTimeStamp);
        add("execution-time", LONG, Slowlog::getExecutionTime);
        add("args", LIST, Slowlog::getArgs);
        add("client-ip-port", STRING, Slowlog::getClientIpPort, ConverterFactory.HOST_AND_PORT::convert);
        add("client-name", STRING, Slowlog::getClientName);
    }};

    public static final ObjectType<StreamEntry> STREAM_ENTRY = new ObjectType<>() {{
        add("id", STRING, e -> ConverterFactory.STREAM_ENTRY_ID.convert(e.getID()));
        add("fields", MAP, StreamEntry::getFields);
    }};

    public static final ObjectType<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new ObjectType<>() {{
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


    public static final ObjectType<KeyValue<String, List<String>>> KEYED_STRING_LIST = new KeyedListType<>() {
        @Override
        protected Function<List<String>, List<?>> getValuesConverter() {
            return ConverterFactory.STRING::convertList;
        }
    };

    public static final ObjectType<KeyValue<String, List<Tuple>>> KEYED_TUPLE_LIST = new KeyedListType<>() {
        @Override
        protected Function<List<Tuple>, List<?>> getValuesConverter() {
            return ConverterFactory.TUPLE::convertList;
        }
    };

    private static abstract class KeyedListType<T> extends ObjectType<KeyValue<String, List<T>>> {
        KeyedListType() {
            add("key", STRING, KeyValue::getKey);
            add("values", LIST, KeyValue::getValue, getValuesConverter());
        }

        protected abstract Function<List<T>, List<?>> getValuesConverter();
    }



    public static final ObjectType<ScanResult<String>> STRING_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected Function<List<String>, List<?>> getResultsConverter() {
            return ConverterFactory.STRING::convertList;
        }
    };

    public static final ObjectType<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected Function<List<Tuple>, List<?>> getResultsConverter() {
            return ConverterFactory.TUPLE::convertList;
        }
    };

    public static final ObjectType<Map.Entry<String, String>> ENTRY = new ObjectType<>() {{
        add("field", STRING, Map.Entry::getKey);
        add("value", STRING, Map.Entry::getValue);
    }};

    public static final ObjectType<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ScanResultType<>() {
        @Override
        protected Function<List<Map.Entry<String, String>>, List<?>> getResultsConverter() {
            return ConverterFactory.ENTRY::convertList;
        }
    };

    private static abstract class ScanResultType<T> extends ObjectType<ScanResult<T>> {
        ScanResultType() {
            add("cursor", STRING, ScanResult::getCursor);
            add("results", LIST, ScanResult::getResult, getResultsConverter());
        }

        protected abstract Function<List<T>, List<?>> getResultsConverter();
    }
}
