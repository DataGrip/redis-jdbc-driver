package jdbc.client.helpers.result.parser.converter;

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
        public Map<String, Object> convert(Tuple encoded) {
            return null;
        }
    };

    public static final ObjectConverter<KeyedListElement> KEYED_LIST_ELEMENT = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(KeyedListElement encoded) {
            return null;
        }
    };
    
    public static final ObjectConverter<KeyedZSetElement> KEYED_ZSET_ELEMENT = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(KeyedZSetElement encoded) {
            return null;
        }
    };

    public static final ObjectConverter<GeoCoordinate> GEO_COORDINATE = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(GeoCoordinate encoded) {
            return null;
        }
    };

    public static final ObjectConverter<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(GeoRadiusResponse encoded) {
            return null;
        }
    };

    public static final ObjectConverter<Module> MODULE = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(Module encoded) {
            return null;
        }
    };

    public static final ObjectConverter<AccessControlUser> ACCESS_CONTROL_USER = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(AccessControlUser encoded) {
            return null;
        }
    };

    public static final ObjectConverter<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(AccessControlLogEntry encoded) {
            return null;
        }
    };

    public static final SimpleConverter<StreamEntryID> STREAM_ENTRY_ID = new SimpleConverter<>() {
        @Override
        public Object convert(StreamEntryID encoded) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<StreamEntry> STREAM_ENTRY = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(StreamEntry encoded) {
            return new HashMap<>() {{
                put("id", STREAM_ENTRY_ID.convert(encoded.getID()));
                put("fields", encoded.getFields());
            }};
        }
    };

    public static final ObjectConverter<Map.Entry<String, List<StreamEntry>>> STREAM_READ = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(Map.Entry<String, List<StreamEntry>> encoded) {
            return null;
        }
    };

    public static final ObjectConverter<StreamInfo> STREAM_INFO = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(StreamInfo encoded) {
            return null;
        }
    };

    public static final ObjectConverter<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(StreamGroupInfo encoded) {
            return null;
        }
    };

    public static final ObjectConverter<StreamConsumersInfo> STREAM_CONSUMERS_INFO = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(StreamConsumersInfo encoded) {
            return null;
        }
    };

    public static final ObjectConverter<ScanResult<String>> STRING_SCAN_RESULT = new ObjectConverter<>() {
        @Override
        public Map<String, Object> convert(ScanResult<String> encoded) {
            return new HashMap<>() {{
                put("cursor", encoded.getCursor());
                put("results", encoded.getResult());
            }};
        }
    };
}
