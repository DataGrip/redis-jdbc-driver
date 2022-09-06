package jdbc.client.helpers.result.parser.converter;

import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConverterFactory {

    private ConverterFactory() {
    }

    public static final Converter<Tuple> TUPLE = new Converter<>() {
        @Override
        public Map<String, Object> convert(Tuple encoded) {
            return null;
        }
    };

    public static final Converter<KeyedListElement> KEYED_LIST_ELEMENT = new Converter<>() {
        @Override
        public Map<String, Object> convert(KeyedListElement encoded) {
            return null;
        }
    };
    
    public static final Converter<KeyedZSetElement> KEYED_ZSET_ELEMENT = new Converter<>() {
        @Override
        public Map<String, Object> convert(KeyedZSetElement encoded) {
            return null;
        }
    };

    public static final Converter<GeoCoordinate> GEO_COORDINATE = new Converter<>() {
        @Override
        public Map<String, Object> convert(GeoCoordinate encoded) {
            return null;
        }
    };

    public static final Converter<GeoRadiusResponse> GEORADIUS_RESPONSE = new Converter<>() {
        @Override
        public Map<String, Object> convert(GeoRadiusResponse encoded) {
            return null;
        }
    };

    public static final Converter<Module> MODULE = new Converter<>() {
        @Override
        public Map<String, Object> convert(Module encoded) {
            return null;
        }
    };

    public static final Converter<AccessControlUser> ACCESS_CONTROL_USER = new Converter<>() {
        @Override
        public Map<String, Object> convert(AccessControlUser encoded) {
            return null;
        }
    };

    public static final Converter<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new Converter<>() {
        @Override
        public Map<String, Object> convert(AccessControlLogEntry encoded) {
            return null;
        }
    };

    public static final Converter<StreamEntryID> STREAM_ENTRY_ID = new Converter<>() {
        @Override
        public Map<String, Object> convert(StreamEntryID encoded) {
            // TODO: return encoded.toString();
            return null;
        }
    };

    public static final Converter<StreamEntry> STREAM_ENTRY = new Converter<>() {
        @Override
        public Map<String, Object> convert(StreamEntry encoded) {
            return null;
        }
    };

    public static final Converter<Map.Entry<String, List<StreamEntry>>> STREAM_READ = new Converter<>() {
        @Override
        public Map<String, Object> convert(Map.Entry<String, List<StreamEntry>> encoded) {
            return null;
        }
    };

    public static final Converter<StreamInfo> STREAM_INFO = new Converter<>() {
        @Override
        public Map<String, Object> convert(StreamInfo encoded) {
            return null;
        }
    };

    public static final Converter<StreamGroupInfo> STREAM_GROUP_INFO = new Converter<>() {
        @Override
        public Map<String, Object> convert(StreamGroupInfo encoded) {
            return null;
        }
    };

    public static final Converter<StreamConsumersInfo> STREAM_CONSUMERS_INFO = new Converter<>() {
        @Override
        public Map<String, Object> convert(StreamConsumersInfo encoded) {
            return null;
        }
    };

    public static final Converter<ScanResult<String>> STRING_SCAN_RESULT = new Converter<>() {
        @Override
        public Map<String, Object> convert(ScanResult<String> encoded) {
            return new LinkedHashMap<>() {{
                put("cursor", encoded.getCursor());
                put("result", encoded.getResult());
            }};
        }
    };
}
