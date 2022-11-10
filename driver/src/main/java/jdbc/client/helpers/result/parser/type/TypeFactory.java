package jdbc.client.helpers.result.parser.type;

import jdbc.client.structures.result.ObjectType;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.resps.*;

import java.util.List;
import java.util.Map;

public class TypeFactory {
    
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

    public static final String STREAM_ENTRY_ID = STRING;

    public static final ObjectType<StreamEntry> STREAM_ENTRY = new ObjectType<>() {{
        add("id", STREAM_ENTRY_ID);
        add("fields", MAP);
    }};

    public static final ObjectType<Map.Entry<String, List<StreamEntry>>> STREAM_READ = new ObjectType<>() {{
        add("key", STRING);
        add("value", MAP);
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

    public static final ObjectType<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectType<>() {{
        add("name", STRING);
        add("consumers", LONG);
        add("pending", LONG);
        add("last-delivered-id", STRING);
    }};

    public static final ObjectType<StreamConsumersInfo> STREAM_CONSUMERS_INFO = new ObjectType<>() {{
        add("name", STRING);
        add("idle", LONG);
        add("pending", LONG);
    }};

    public static final ObjectType<ScanResult<String>> STRING_SCAN_RESULT = new ScanResultType<>();

    public static final ObjectType<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ScanResultType<>();

    private static class ScanResultType<T> extends ObjectType<ScanResult<T>> {
        ScanResultType() {
            add("cursor", STRING);
            add("results", ARRAY);
        }
    }
}
