package jdbc.client.result.parser.converter;

import jdbc.client.query.structures.Params;
import jdbc.client.result.structures.ObjectType;
import jdbc.client.result.structures.SimpleType;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Module;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.SearchResult;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.timeseries.TSElement;
import redis.clients.jedis.timeseries.TSKeyValue;
import redis.clients.jedis.timeseries.TSKeyedElements;
import redis.clients.jedis.util.KeyValue;

import java.util.List;
import java.util.Map;

public class ConverterFactory {

    private ConverterFactory() {
    }


    /* --------------------------------------------- Common --------------------------------------------- */

    public static final IdentityConverter<Object> OBJECT = new IdentityConverter<>() {
        @Override
        public SimpleType<Object> getSimpleType() {
            return TypeFactory.OBJECT;
        }
    };

    public static final IdentityConverter<String> STRING = new IdentityConverter<>() {
        @Override
        public SimpleType<String> getSimpleType() {
            return TypeFactory.STRING;
        }
    };

    public static final IdentityConverter<Long> LONG = new IdentityConverter<>() {
        @Override
        public SimpleType<Long> getSimpleType() {
            return TypeFactory.LONG;
        }
    };

    public static final IdentityConverter<Double> DOUBLE = new IdentityConverter<>() {
        @Override
        public SimpleType<Double> getSimpleType() {
            return TypeFactory.DOUBLE;
        }
    };

    public static final IdentityConverter<Boolean> BOOLEAN = new IdentityConverter<>() {
        @Override
        public SimpleType<Boolean> getSimpleType() {
            return TypeFactory.BOOLEAN;
        }
    };

    public static final IdentityConverter<byte[]> BYTE_ARRAY = new IdentityConverter<>() {
        @Override
        public SimpleType<byte[]> getSimpleType() {
            return TypeFactory.BYTE_ARRAY;
        }
    };

    public static final IdentityConverter<List<Object>> OBJECT_LIST = new IdentityConverter<>() {
        private final SimpleType<List<Object>> OBJECT_LIST = new TypeFactory.ListSimpleType<>();

        @Override
        public SimpleType<List<Object>> getSimpleType() {
            return OBJECT_LIST;
        }
    };

    public static final IdentityConverter<List<String>> STRING_LIST = new IdentityConverter<>() {
        private final SimpleType<List<String>> STRING_LIST = new TypeFactory.ListSimpleType<>();

        @Override
        public SimpleType<List<String>> getSimpleType() {
            return STRING_LIST;
        }
    };


    /* --------------------------------------------- Native --------------------------------------------- */

    public static final ObjectConverter<KeyedListElement> KEYED_STRING = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyedListElement> getObjectType() {
            return TypeFactory.KEYED_STRING;
        }
    };

    public static final ObjectConverter<Tuple> TUPLE = new ObjectConverter<>() {
        @Override
        public ObjectType<Tuple> getObjectType() {
            return TypeFactory.TUPLE;
        }
    };

    public static final ObjectConverter<KeyedZSetElement> KEYED_TUPLE = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyedZSetElement> getObjectType() {
            return TypeFactory.KEYED_TUPLE;
        }
    };


    public static final ObjectConverter<KeyValue<String, List<String>>> KEYED_STRING_LIST = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyValue<String, List<String>>> getObjectType() {
            return TypeFactory.KEYED_STRING_LIST;
        }
    };

    public static final ObjectConverter<KeyValue<String, List<Tuple>>> KEYED_TUPLE_LIST = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyValue<String, List<Tuple>>> getObjectType() {
            return TypeFactory.KEYED_TUPLE_LIST;
        }
    };


    public static final ObjectConverter<ScanResult<String>> STRING_SCAN_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<ScanResult<String>> getObjectType() {
            return TypeFactory.STRING_SCAN_RESULT;
        }
    };

    public static final ObjectConverter<ScanResult<Tuple>> TUPLE_SCAN_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<ScanResult<Tuple>> getObjectType() {
            return TypeFactory.TUPLE_SCAN_RESULT;
        }
    };

    public static final ObjectConverter<Map.Entry<String, String>> ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<String, String>> getObjectType() {
            return TypeFactory.ENTRY;
        }
    };

    public static final ObjectConverter<ScanResult<Map.Entry<String, String>>> ENTRY_SCAN_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<ScanResult<Map.Entry<String, String>>> getObjectType() {
            return TypeFactory.ENTRY_SCAN_RESULT;
        }
    };


    public static final SimpleConverter<StreamEntryID, String> STREAM_ENTRY_ID = new SimpleConverter<>() {
        @Override
        public SimpleType<String> getSimpleType() {
            return TypeFactory.STRING;
        }

        @Override
        public @NotNull String convertImpl(@NotNull StreamEntryID encoded, @NotNull Params params) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<StreamEntry> STREAM_ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamEntry> getObjectType() {
            return TypeFactory.STREAM_ENTRY;
        }
    };

    public static final ObjectConverter<Map.Entry<StreamEntryID, List<StreamEntry>>> STREAM_AUTO_CLAIM_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<StreamEntryID, List<StreamEntry>>> getObjectType() {
            return TypeFactory.STREAM_AUTO_CLAIM_RESPONSE;
        }
    };

    public static final ObjectConverter<Map.Entry<StreamEntryID, List<StreamEntryID>>> STREAM_AUTO_CLAIM_ID_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<StreamEntryID, List<StreamEntryID>>> getObjectType() {
            return TypeFactory.STREAM_AUTO_CLAIM_ID_RESPONSE;
        }
    };

    public static final ObjectConverter<Map.Entry<String, List<StreamEntry>>> STREAM_READ_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<String, List<StreamEntry>>> getObjectType() {
            return TypeFactory.STREAM_READ_RESPONSE;
        }
    };

    public static final ObjectConverter<StreamConsumersInfo> STREAM_CONSUMER_INFO = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamConsumersInfo> getObjectType() {
            return TypeFactory.STREAM_CONSUMER_INFO;
        }
    };

    public static final ObjectConverter<StreamConsumerFullInfo> STREAM_CONSUMER_INFO_FULL = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamConsumerFullInfo> getObjectType() {
            return TypeFactory.STREAM_CONSUMER_INFO_FULL;
        }
    };

    public static final ObjectConverter<StreamGroupInfo> STREAM_GROUP_INFO = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamGroupInfo> getObjectType() {
            return TypeFactory.STREAM_GROUP_INFO;
        }
    };

    public static final ObjectConverter<StreamGroupFullInfo> STREAM_GROUP_INFO_FULL = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamGroupFullInfo> getObjectType() {
            return TypeFactory.STREAM_GROUP_INFO_FULL;
        }
    };

    public static final ObjectConverter<StreamInfo> STREAM_INFO = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamInfo> getObjectType() {
            return TypeFactory.STREAM_INFO;
        }
    };

    public static final ObjectConverter<StreamFullInfo> STREAM_INFO_FULL = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamFullInfo> getObjectType() {
            return TypeFactory.STREAM_INFO_FULL;
        }
    };

    public static final ObjectConverter<StreamPendingEntry> STREAM_PENDING_ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamPendingEntry> getObjectType() {
            return TypeFactory.STREAM_PENDING_ENTRY;
        }
    };

    public static final ObjectConverter<StreamPendingSummary> STREAM_PENDING_SUMMARY = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamPendingSummary> getObjectType() {
            return TypeFactory.STREAM_PENDING_SUMMARY;
        }
    };


    public static final ObjectConverter<GeoCoordinate> GEO_COORDINATE = new ObjectConverter<>() {
        @Override
        public ObjectType<GeoCoordinate> getObjectType() {
            return TypeFactory.GEO_COORDINATE;
        }
    };

    public static final ObjectConverter<GeoRadiusResponse> GEORADIUS_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<GeoRadiusResponse> getObjectType() {
            return TypeFactory.GEORADIUS_RESPONSE;
        }
    };

    public static final ObjectConverter<Module> MODULE = new ObjectConverter<>() {
        @Override
        public ObjectType<Module> getObjectType() {
            return TypeFactory.MODULE;
        }
    };

    public static final ObjectConverter<AccessControlUser> ACCESS_CONTROL_USER = new ObjectConverter<>() {
        @Override
        public ObjectType<AccessControlUser> getObjectType() {
            return TypeFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ObjectConverter<AccessControlLogEntry> ACCESS_CONTROL_LOG_ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<AccessControlLogEntry> getObjectType() {
            return TypeFactory.ACCESS_CONTROL_LOG_ENTRY;
        }
    };

    public static final ObjectConverter<CommandDocument> COMMAND_DOCUMENT = new ObjectConverter<>() {
        @Override
        public ObjectType<CommandDocument> getObjectType() {
            return TypeFactory.COMMAND_DOCUMENT;
        }
    };

    public static final ObjectConverter<CommandInfo> COMMAND_INFO = new ObjectConverter<>() {
        @Override
        public ObjectType<CommandInfo> getObjectType() {
            return TypeFactory.COMMAND_INFO;
        }
    };

    public static final ObjectConverter<FunctionStats> FUNCTION_STATS = new ObjectConverter<>() {
        @Override
        public ObjectType<FunctionStats> getObjectType() {
            return TypeFactory.FUNCTION_STATS;
        }
    };

    public static final ObjectConverter<LibraryInfo> LIBRARY_INFO = new ObjectConverter<>() {
        @Override
        public ObjectType<LibraryInfo> getObjectType() {
            return TypeFactory.LIBRARY_INFO;
        }
    };

    public static final SimpleConverter<HostAndPort, String> HOST_AND_PORT = new SimpleConverter<>() {
        @Override
        public SimpleType<String> getSimpleType() {
            return TypeFactory.STRING;
        }

        @Override
        protected @NotNull String convertImpl(@NotNull HostAndPort encoded, @NotNull Params params) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<Slowlog> SLOW_LOG = new ObjectConverter<>() {
        @Override
        public ObjectType<Slowlog> getObjectType() {
            return TypeFactory.SLOW_LOG;
        }
    };

    public static ObjectConverter<LCSMatchResult.Position> LCS_POSITION = new ObjectConverter<>() {
        @Override
        public ObjectType<LCSMatchResult.Position> getObjectType() {
            return TypeFactory.LCS_POSITION;
        }
    };

    public static final ObjectConverter<LCSMatchResult.MatchedPosition> LCS_MATCHED_POSITION = new ObjectConverter<>() {
        @Override
        public ObjectType<LCSMatchResult.MatchedPosition> getObjectType() {
            return TypeFactory.LCS_MATCHED_POSITION;
        }
    };

    public static final ObjectConverter<LCSMatchResult> LCS_MATCH_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<LCSMatchResult> getObjectType() {
            return TypeFactory.LCS_MATCH_RESULT;
        }
    };


    public static final ObjectConverter<KeyValue<Long, Long>> WAITAOF_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyValue<Long, Long>> getObjectType() {
            return TypeFactory.WAITAOF_RESPONSE;
        }
    };

    public static final ObjectConverter<KeyValue<Long, Double>> ZRANK_WITHSCORE_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyValue<Long, Double>> getObjectType() {
            return TypeFactory.ZRANK_WITHSCORE_RESPONSE;
        }
    };


    /* --------------------------------------------- RedisJSON --------------------------------------------- */


    /* --------------------------------------------- RediSearch --------------------------------------------- */

    public static final ObjectConverter<AggregationResult> AGGREGATION_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<AggregationResult> getObjectType() {
            return TypeFactory.AGGREGATION_RESULT;
        }
    };

    public static final ObjectConverter<Map.Entry<AggregationResult, Map<String, Object>>> AGGREGATION_PROFILE_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<AggregationResult, Map<String, Object>>> getObjectType() {
            return TypeFactory.AGGREGATION_PROFILE_RESPONSE;
        }
    };

    public static final ObjectConverter<Document> SEARCH_DOCUMENT = new ObjectConverter<>() {
        @Override
        public ObjectType<Document> getObjectType() {
            return TypeFactory.SEARCH_DOCUMENT;
        }
    };

    public static final ObjectConverter<SearchResult> SEARCH_RESULT = new ObjectConverter<>() {
        @Override
        public ObjectType<SearchResult> getObjectType() {
            return TypeFactory.SEARCH_RESULT;
        }
    };

    public static final ObjectConverter<Map.Entry<SearchResult, Map<String, Object>>> SEARCH_PROFILE_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<SearchResult, Map<String, Object>>> getObjectType() {
            return TypeFactory.SEARCH_PROFILE_RESPONSE;
        }
    };


    public static final IdentityConverter<Map<String, Double>> SEARCH_SPELLCHECK_RESPONSE = new IdentityConverter<>() {
        private final SimpleType<Map<String, Double>> SEARCH_SPELLCHECK_RESPONSE = new TypeFactory.MapSimpleType<>();

        @Override
        public SimpleType<Map<String, Double>> getSimpleType() {
            return SEARCH_SPELLCHECK_RESPONSE;
        }
    };


    public static final IdentityConverter<List<String>> SEARCH_SYNONYM_GROUPS = new IdentityConverter<>() {
        private final SimpleType<List<String>> SEARCH_SYNONYM_GROUPS = new TypeFactory.ListSimpleType<>();

        @Override
        public SimpleType<List<String>> getSimpleType() {
            return SEARCH_SYNONYM_GROUPS;
        }
    };


    /* --------------------------------------------- RedisBloom --------------------------------------------- */

    public static final ObjectConverter<Map.Entry<Long, byte[]>> BLOOM_SCANDUMP_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<Long, byte[]>> getObjectType() {
            return TypeFactory.BLOOM_SCANDUMP_RESPONSE;
        }
    };


    /* --------------------------------------------- RedisTimeSeries --------------------------------------------- */

    public static final ObjectConverter<TSElement> TIMESERIES_ELEMENT = new ObjectConverter<>() {
        @Override
        public ObjectType<TSElement> getObjectType() {
            return TypeFactory.TIMESERIES_ELEMENT;
        }
    };

    public static final ObjectConverter<TSKeyValue<TSElement>> TIMESERIES_MGET_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<TSKeyValue<TSElement>> getObjectType() {
            return TypeFactory.TIMESERIES_MGET_RESPONSE;
        }
    };

    public static final ObjectConverter<TSKeyedElements> TIMESERIES_MRANGE_RESPONSE = new ObjectConverter<>() {
        @Override
        public ObjectType<TSKeyedElements> getObjectType() {
            return TypeFactory.TIMESERIES_MRANGE_RESPONSE;
        }
    };


    /* ------------------------------------------------------------------------------------------ */

}
