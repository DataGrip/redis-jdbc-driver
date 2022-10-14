package jdbc.client.helpers.result.parser.type;

import java.util.LinkedHashMap;
import java.util.Map;

public class TypeFactory {

    // TODO: use List instead of LinkedHashMap
    private TypeFactory() {
    }

    public static final String STRING = "string";

    public static final String LONG = "long";

    public static final String DOUBLE = "double";

    public static final String BOOLEAN = "boolean";

    public static final String ARRAY = "array";

    public static final String MAP = "map";

    public static final Map<String, String> TUPLE = new LinkedHashMap<>() {{
        put("value", STRING);
        put("score", DOUBLE);
    }};

    public static final Map<String, String> KEYED_LIST_ELEMENT = new LinkedHashMap<>() {{
        put("key", STRING);
        put("value", STRING);
    }};

    public static final Map<String, String> KEYED_ZSET_ELEMENT = new LinkedHashMap<>() {{
        put("key", STRING);
        put("value", STRING);
        put("score", DOUBLE);
    }};

    public static final Map<String, String> GEO_COORDINATE = new LinkedHashMap<>() {{
        put("longitude", DOUBLE);
        put("latitude", DOUBLE);
    }};

    public static final Map<String, String> GEORADIUS_RESPONSE = new LinkedHashMap<>() {{
        put("member", STRING);
        put("distance", DOUBLE);
        put("coordinate", MAP);
        put("rawScore", LONG);
    }};

    public static final Map<String, String> MODULE = new LinkedHashMap<>() {{
        put("name", STRING);
        put("version", LONG);
    }};

    public static final Map<String, String> ACCESS_CONTROL_USER = new LinkedHashMap<>() {{
        put("flags", ARRAY);
        put("keys", ARRAY);
        put("passwords", ARRAY);
        put("commands", STRING);
    }};;

    public static final Map<String, String> ACCESS_CONTROL_LOG_ENTRY = new LinkedHashMap<>() {{
        put("count", STRING);
        put("reason", STRING);
        put("context", STRING);
        put("object", STRING);
        put("username", STRING);
        put("ageSeconds", STRING);
        put("clientInfo", MAP);
    }};

    public static final String STREAM_ENTRY_ID = STRING;

    public static final Map<String, String> STREAM_ENTRY = new LinkedHashMap<>() {{
        put("id", STREAM_ENTRY_ID);
        put("fields", MAP);
    }};

    public static final Map<String, String> STREAM_READ = new LinkedHashMap<>() {{
        put("key", STRING);
        put("value", MAP);
    }};

    public static final Map<String, String> STREAM_INFO = new LinkedHashMap<>() {{
        put("length", LONG);
        put("radixTreeKeys", LONG);
        put("radixTreeNodes", LONG);
        put("groups", LONG);
        put("lastGeneratedId", STRING);
        put("firstEntry", MAP);
        put("lastEntry", MAP);
    }};

    public static final Map<String, String> STREAM_GROUP_INFO = new LinkedHashMap<>() {{
        put("name", STRING);
        put("consumers", LONG);
        put("pending", LONG);
        put("lastDeliveredId", STRING);
    }};

    public static final Map<String, String> STREAM_CONSUMERS_INFO = new LinkedHashMap<>() {{
        put("name", STRING);
        put("idle", LONG);
        put("pending", LONG);
    }};

    public static final Map<String, String> SCAN_RESULT = new LinkedHashMap<>() {{
        put("cursor", STRING);
        put("results", ARRAY);
    }};
}
