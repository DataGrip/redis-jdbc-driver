package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;

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
                put("rawScore", encoded.getRawScore());
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
                put("ageSeconds", encoded.getAgeSeconds());
                put("clientInfo", encoded.getClientInfo());
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

    public static final ObjectConverter<Map.Entry<String, List<StreamEntry>>> STREAM_READ = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull Map.Entry<String, List<StreamEntry>> encoded) {
            return new HashMap<>() {{
                put("key", encoded.getKey());
                put("value", STREAM_ENTRY.convert(encoded.getValue()));
            }};
        }
    };

    public static final ObjectConverter<StreamInfo> STREAM_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamInfo encoded) {
            return new HashMap<>() {{
                put("length", encoded.getLength());
                put("radixTreeKeys", encoded.getRadixTreeKeys());
                put("radixTreeNodes", encoded.getRadixTreeNodes());
                put("groups", encoded.getGroups());
                put("lastGeneratedId", STREAM_ENTRY_ID.convert(encoded.getLastGeneratedId()));
                put("firstEntry", STREAM_ENTRY.convert(encoded.getFirstEntry()));
                put("lastEntry", STREAM_ENTRY.convert(encoded.getLastEntry()));
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
                put("lastDeliveredId", STREAM_ENTRY_ID.convert(encoded.getLastDeliveredId()));
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

    public static final ObjectConverter<ScanResult<String>> STRING_SCAN_RESULT = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull ScanResult<String> encoded) {
            return new HashMap<>() {{
                put("cursor", encoded.getCursor());
                put("results", encoded.getResult());
            }};
        }
    };
}
