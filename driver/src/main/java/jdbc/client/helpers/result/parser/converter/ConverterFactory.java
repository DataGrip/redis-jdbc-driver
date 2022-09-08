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
            return null;
        }
    };

    public static final ObjectConverter<KeyedListElement> KEYED_LIST_ELEMENT = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull KeyedListElement encoded) {
            return null;
        }
    };
    
    public static final ObjectConverter<KeyedZSetElement> KEYED_ZSET_ELEMENT = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull KeyedZSetElement encoded) {
            return null;
        }
    };

    public static final ObjectConverter<GeoCoordinate> GEO_COORDINATE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull GeoCoordinate encoded) {
            return null;
        }
    };

    public static final ObjectConverter<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull GeoRadiusResponse encoded) {
            return null;
        }
    };

    public static final ObjectConverter<Module> MODULE = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull Module encoded) {
            return null;
        }
    };

    public static final ObjectConverter<AccessControlUser> ACCESS_CONTROL_USER = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull AccessControlUser encoded) {
            return null;
        }
    };

    public static final ObjectConverter<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull AccessControlLogEntry encoded) {
            return null;
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
            return null;
        }
    };

    public static final ObjectConverter<StreamInfo> STREAM_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamInfo encoded) {
            return null;
        }
    };

    public static final ObjectConverter<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamGroupInfo encoded) {
            return null;
        }
    };

    public static final ObjectConverter<StreamConsumersInfo> STREAM_CONSUMERS_INFO = new ObjectConverter<>() {
        @Override
        public @NotNull Map<String, Object> convertImpl(@NotNull StreamConsumersInfo encoded) {
            return null;
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
