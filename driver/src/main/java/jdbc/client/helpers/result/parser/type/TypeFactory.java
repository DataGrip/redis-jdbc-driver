package jdbc.client.helpers.result.parser.type;

import jdbc.client.structures.result.ObjectType;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.resps.*;

import java.util.List;
import java.util.Map;

public class TypeFactory {

    // TODO: combine Type & Converter for objects
    private TypeFactory() {
    }

    public static final String STRING = "string";

    public static final String LONG = "long";

    public static final String DOUBLE = "double";

    public static final String BOOLEAN = "boolean";

    public static final String ARRAY = "array";

    public static final String MAP = "map";

    public static final String BYTE_ARRAY = "binary";

    public static final ObjectType<Tuple> TUPLE = new ObjectType<>() {{
        add("value", STRING);
        add("score", DOUBLE);
    }};

    public static final ObjectType<KeyedListElement> KEYED_LIST_ELEMENT = new ObjectType<>() {{
        add("key", STRING);
        add("value", STRING);
    }};

    public static final ObjectType<KeyedZSetElement> KEYED_ZSET_ELEMENT = new ObjectType<>() {{
        add("key", STRING);
        add("value", STRING);
        add("score", DOUBLE);
    }};

    public static final ObjectType<GeoCoordinate> GEO_COORDINATE = new ObjectType<>() {{
        add("longitude", DOUBLE);
        add("latitude", DOUBLE);
    }};

    public static final ObjectType<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectType<>() {{
        add("member", STRING);
        add("distance", DOUBLE, q -> q.containsParam(Keyword.WITHDIST));
        add("coordinate", MAP, q -> q.containsParam(Keyword.WITHCOORD));
        add("raw-score", LONG, q -> q.containsParam(Keyword.WITHHASH));
    }};

    public static final ObjectType<Module> MODULE = new ObjectType<>() {{
        add("name", STRING);
        add("version", LONG);
    }};

    public static final ObjectType<AccessControlUser> ACCESS_CONTROL_USER = new ObjectType<>() {{
        add("flags", ARRAY);
        add("keys", ARRAY);
        add("passwords", ARRAY);
        add("commands", STRING);
    }};

    public static final ObjectType<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectType<>() {{
        add("count", STRING);
        add("reason", STRING);
        add("context", STRING);
        add("object", STRING);
        add("username", STRING);
        add("age-seconds", STRING);
        add("client-info", MAP);
    }};

    // TODO: CommandDocument: arguments?
    public static final ObjectType<CommandDocument> COMMAND_DOCUMENT = new ObjectType<>() {{
        add("command-name", STRING);
        add("summary", STRING);
        add("since", STRING);
        add("group", STRING);
        add("complexity", STRING);
        add("history", ARRAY);
    }};

    public static final ObjectType<CommandInfo> COMMAND_INFO = new ObjectType<>() {{
        add("command-name", STRING);
        add("arity", LONG);
        add("flags", STRING);
        add("firstKey", LONG);
        add("lastKey", LONG);
        add("step", LONG);
        add("acl-categories", ARRAY);
        add("tips", ARRAY);
        add("subcommands", ARRAY);
    }};

    public static final ObjectType<FunctionStats> FUNCTION_STATS = new ObjectType<>() {{
        add("running-script", MAP);
        add("engines", MAP);
    }};

    public static final ObjectType<LibraryInfo> LIBRARY_INFO = new ObjectType<>() {{
        add("library-name", STRING);
        add("engine", STRING);
        add("functions", MAP);
        add("library-code", STRING, q -> q.containsParam(Keyword.WITHCODE));
    }};

    public static final ObjectType<Slowlog> SLOW_LOG = new ObjectType<>() {{
        add("id", LONG);
        add("timestamp", LONG);
        add("execution-time", LONG);
        add("args", ARRAY);
        add("client-ip-port", STRING);
        add("client-name", STRING);
    }};

    public static final String STREAM_ENTRY_ID = STRING;

    public static final ObjectType<StreamEntry> STREAM_ENTRY = new ObjectType<>() {{
        add("id", STREAM_ENTRY_ID);
        add("fields", MAP);
    }};

    public static final ObjectType<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new ObjectType<>() {{
        add("key", STRING);
        add("entries", MAP);
    }};

    public static final ObjectType<StreamConsumersInfo> STREAM_CONSUMER_INFO = new ObjectType<>() {{
        add("name", STRING);
        add("idle", LONG);
        add("pending", LONG);
    }};

    public static final ObjectType<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectType<>() {{
        add("name", STRING);
        add("consumers", LONG);
        add("pending", LONG);
        add("last-delivered-id", STRING);
    }};

    public static final ObjectType<StreamInfo> STREAM_INFO = new ObjectType<>() {{
        add("length", LONG);
        add("radix-tree-keys", LONG);
        add("radix-tree-nodes", LONG);
        add("groups", LONG);
        add("last-generated-id", STRING);
        add("first-entry", MAP);
        add("last-entry", MAP);
    }};

    public static final ObjectType<StreamFullInfo> STREAM_INFO_FULL = new ObjectType<>() {{
        add("length", LONG);
        add("radix-tree-keys", LONG);
        add("radix-tree-nodes", LONG);
        add("groups", ARRAY);
        add("last-generated-id", STRING);
        add("entries", ARRAY);
    }};

    public static final ObjectType<StreamPendingEntry> STREAM_PENDING_ENTRY = new ObjectType<>() {{
        add("id", STRING);
        add("consumer-name", STRING);
        add("idle-time", LONG);
        add("delivered-times", LONG);
    }};

    public static final ObjectType<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ObjectType<>() {{
        add("total", LONG);
        add("min-id", STRING);
        add("max-id", STRING);
        add("consumer-message-count", MAP);
    }};

    public static final ObjectType<ScanResult<String>> STRING_SCAN_RESULT = new ScanResultType<>();

    public static final ObjectType<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ScanResultType<>();

    public static final ObjectType<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ScanResultType<>();

    private static class ScanResultType<T> extends ObjectType<ScanResult<T>> {
        ScanResultType() {
            add("cursor", STRING);
            add("results", ARRAY);
        }
    }
}
