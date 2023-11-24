package jdbc.client.result.parser;

import jdbc.client.query.structures.RedisQuery;
import jdbc.client.result.parser.converter.ConverterFactory;
import jdbc.client.result.parser.converter.IdentityConverter;
import jdbc.client.result.parser.converter.ObjectConverter;
import jdbc.client.result.parser.converter.SimpleConverter;
import jdbc.client.result.parser.encoder.EncoderFactory;
import jdbc.client.result.parser.encoder.ListEncoder;
import jdbc.client.result.parser.encoder.MapEncoder;
import jdbc.client.result.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.util.KeyValue;

import java.util.List;
import java.util.Map;

public class ResultParserFactory {

    private ResultParserFactory() {
    }


    /* --------------------------------------------- Common --------------------------------------------- */

    public static final ResultParser OBJECT = new ListResultParser<>() {
        @Override
        protected @NotNull ListEncoder<Object> getBuilder() {
            return EncoderFactory.OBJECT;
        }

        @Override
        protected @NotNull IdentityConverter<Object> getConverter() {
            return ConverterFactory.OBJECT;
        }
    };

    public static final ResultParser STRING = new ListResultParser<String, String>() {
        @Override
        protected @NotNull ListEncoder<String> getBuilder() {
            return EncoderFactory.STRING;
        }

        @Override
        protected @NotNull IdentityConverter<String> getConverter() {
            return ConverterFactory.STRING;
        }
    };

    public static final ResultParser LONG = new ListResultParser<Long, Long>() {
        @Override
        protected @NotNull ListEncoder<Long> getBuilder() {
            return EncoderFactory.LONG;
        }

        @Override
        protected @NotNull IdentityConverter<Long> getConverter() {
            return ConverterFactory.LONG;
        }
    };

    public static final ResultParser DOUBLE = new ListResultParser<Double, Double>() {
        @Override
        protected @NotNull ListEncoder<Double> getBuilder() {
            return EncoderFactory.DOUBLE;
        }

        @Override
        protected @NotNull IdentityConverter<Double> getConverter() {
            return ConverterFactory.DOUBLE;
        }
    };

    public static final ResultParser BOOLEAN = new ListResultParser<Boolean, Boolean>() {
        @Override
        protected @NotNull ListEncoder<Boolean> getBuilder() {
            return EncoderFactory.BOOLEAN;
        }

        @Override
        protected @NotNull IdentityConverter<Boolean> getConverter() {
            return ConverterFactory.BOOLEAN;
        }
    };

    public static final ResultParser BYTE_ARRAY = new ListResultParser<byte[], byte[]>() {
        @Override
        protected @NotNull ListEncoder<byte[]> getBuilder() {
            return EncoderFactory.BYTE_ARRAY;
        }

        @Override
        protected @NotNull IdentityConverter<byte[]> getConverter() {
            return ConverterFactory.BYTE_ARRAY;
        }
    };


    public static final ResultParser OBJECT_LIST = new ListResultParser<List<Object>, List<Object>>() {
        @Override
        protected @NotNull ListEncoder<List<Object>> getBuilder() {
            return EncoderFactory.OBJECT_LIST;
        }

        @Override
        protected @NotNull IdentityConverter<List<Object>> getConverter() {
            return ConverterFactory.OBJECT_LIST;
        }
    };

    public static final ResultParser STRING_LIST = new ListResultParser<List<String>, List<String>>() {
        @Override
        protected @NotNull ListEncoder<List<String>> getBuilder() {
            return EncoderFactory.STRING_LIST;
        }

        @Override
        protected @NotNull IdentityConverter<List<String>> getConverter() {
            return ConverterFactory.STRING_LIST;
        }
    };


    public static final ResultParser OBJECT_MAP = new MapResultParser<>() {
        @Override
        protected @NotNull MapEncoder<Object> getBuilder() {
            return EncoderFactory.OBJECT_MAP;
        }

        @Override
        protected @NotNull IdentityConverter<Object> getConverter() {
            return ConverterFactory.OBJECT;
        }
    };

    public static final ResultParser STRING_MAP = new MapResultParser<String, String>() {
        @Override
        protected @NotNull MapEncoder<String> getBuilder() {
            return EncoderFactory.STRING_MAP;
        }

        @Override
        protected @NotNull IdentityConverter<String> getConverter() {
            return ConverterFactory.STRING;
        }
    };


    public static final ResultParser KEYED_STRING = new ObjectListResultParser<KeyedListElement>() {
        @Override
        protected @NotNull ListEncoder<KeyedListElement> getBuilder() {
            return EncoderFactory.KEYED_STRING;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedListElement> getConverter() {
            return ConverterFactory.KEYED_STRING;
        }
    };

    public static final ResultParser TUPLE = new ObjectListResultParser<Tuple>() {
        @Override
        protected @NotNull ListEncoder<Tuple> getBuilder() {
            return EncoderFactory.TUPLE;
        }

        @Override
        protected @NotNull ObjectConverter<Tuple> getConverter() {
            return ConverterFactory.TUPLE;
        }
    };

    public static final ResultParser KEYED_TUPLE = new ObjectListResultParser<KeyedZSetElement>() {
        @Override
        protected @NotNull ListEncoder<KeyedZSetElement> getBuilder() {
            return EncoderFactory.KEYED_TUPLE;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedZSetElement> getConverter() {
            return ConverterFactory.KEYED_TUPLE;
        }
    };

    public static final ResultParser GEO_COORDINATE = new ObjectListResultParser<GeoCoordinate>() {
        @Override
        protected @NotNull ListEncoder<GeoCoordinate> getBuilder() {
            return EncoderFactory.GEO_COORDINATE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoCoordinate> getConverter() {
            return ConverterFactory.GEO_COORDINATE;
        }
    };

    public static final ResultParser GEORADIUS_RESPONSE = new ObjectListResultParser<GeoRadiusResponse>() {
        @Override
        protected @NotNull ListEncoder<GeoRadiusResponse> getBuilder() {
            return EncoderFactory.GEORADIUS_RESPONSE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoRadiusResponse> getConverter() {
            return ConverterFactory.GEORADIUS_RESPONSE;
        }
    };

    public static final ResultParser MODULE = new ObjectListResultParser<Module>() {
        @Override
        protected @NotNull ListEncoder<Module> getBuilder() {
            return EncoderFactory.MODULE;
        }

        @Override
        protected @NotNull ObjectConverter<Module> getConverter() {
            return ConverterFactory.MODULE;
        }
    };

    public static final ResultParser ACCESS_CONTROL_USER = new ObjectListResultParser<AccessControlUser>() {
        @Override
        protected @NotNull ListEncoder<AccessControlUser> getBuilder() {
            return EncoderFactory.ACCESS_CONTROL_USER;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlUser> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ResultParser ACCESS_CONTROL_LOG_ENTRY = new ObjectListResultParser<AccessControlLogEntry>() {
        @Override
        protected @NotNull ListEncoder<AccessControlLogEntry> getBuilder() {
            return EncoderFactory.ACCESS_CONTROL_LOG_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlLogEntry> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_LOG_ENTRY;
        }
    };

    public static final ResultParser COMMAND_DOCUMENT = new ObjectMapResultParser<CommandDocument>() {
        @Override
        protected @NotNull MapEncoder<CommandDocument> getBuilder() {
            return EncoderFactory.COMMAND_DOCUMENT;
        }

        @Override
        protected @NotNull ObjectConverter<CommandDocument> getConverter() {
            return ConverterFactory.COMMAND_DOCUMENT;
        }
    };

    public static final ResultParser COMMAND_INFO = new ObjectMapResultParser<CommandInfo>() {
        @Override
        protected @NotNull MapEncoder<CommandInfo> getBuilder() {
            return EncoderFactory.COMMAND_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<CommandInfo> getConverter() {
            return ConverterFactory.COMMAND_INFO;
        }
    };

    public static final ResultParser FUNCTION_STATS = new ObjectListResultParser<FunctionStats>() {
        @Override
        protected @NotNull ListEncoder<FunctionStats> getBuilder() {
            return EncoderFactory.FUNCTION_STATS;
        }

        @Override
        protected @NotNull ObjectConverter<FunctionStats> getConverter() {
            return ConverterFactory.FUNCTION_STATS;
        }
    };

    public static final ResultParser LIBRARY_INFO = new ObjectListResultParser<LibraryInfo>() {
        @Override
        protected @NotNull ListEncoder<LibraryInfo> getBuilder() {
            return EncoderFactory.LIBRARY_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<LibraryInfo> getConverter() {
            return ConverterFactory.LIBRARY_INFO;
        }
    };

    public static final ResultParser SLOW_LOG = new ObjectListResultParser<Slowlog>() {
        @Override
        protected @NotNull ListEncoder<Slowlog> getBuilder() {
            return EncoderFactory.SLOW_LOG;
        }

        @Override
        protected @NotNull ObjectConverter<Slowlog> getConverter() {
            return ConverterFactory.SLOW_LOG;
        }
    };

    public static final ResultParser STREAM_ENTRY_ID = new ListResultParser<StreamEntryID, String>() {
        @Override
        protected @NotNull ListEncoder<StreamEntryID> getBuilder() {
            return EncoderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull SimpleConverter<StreamEntryID, String> getConverter() {
            return ConverterFactory.STREAM_ENTRY_ID;
        }
    };

    public static final ResultParser STREAM_ENTRY = new ObjectListResultParser<StreamEntry>() {
        @Override
        protected @NotNull ListEncoder<StreamEntry> getBuilder() {
            return EncoderFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamEntry> getConverter() {
            return ConverterFactory.STREAM_ENTRY;
        }
    };

    public static final ResultParser STREAM_READ = new ObjectListResultParser<Map.Entry<String, List<StreamEntry>>>() {
        @Override
        protected @NotNull ListEncoder<Map.Entry<String, List<StreamEntry>>> getBuilder() {
            return EncoderFactory.STREAM_READ_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<Map.Entry<String, List<StreamEntry>>> getConverter() {
            return ConverterFactory.STREAM_READ_ENTRY;
        }
    };

    public static final ResultParser STREAM_CONSUMER_INFO = new ObjectListResultParser<StreamConsumersInfo>() {
        @Override
        protected @NotNull ListEncoder<StreamConsumersInfo> getBuilder() {
            return EncoderFactory.STREAM_CONSUMER_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamConsumersInfo> getConverter() {
            return ConverterFactory.STREAM_CONSUMER_INFO;
        }
    };

    public static final ResultParser STREAM_GROUP_INFO = new ObjectListResultParser<StreamGroupInfo>() {
        @Override
        protected @NotNull ListEncoder<StreamGroupInfo> getBuilder() {
            return EncoderFactory.STREAM_GROUP_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamGroupInfo> getConverter() {
            return ConverterFactory.STREAM_GROUP_INFO;
        }
    };

    public static final ResultParser STREAM_INFO = new ObjectListResultParser<StreamInfo>() {
        @Override
        protected @NotNull ListEncoder<StreamInfo> getBuilder() {
            return EncoderFactory.STREAM_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamInfo> getConverter() {
            return ConverterFactory.STREAM_INFO;
        }
    };

    public static final ResultParser STREAM_INFO_FULL = new ObjectListResultParser<StreamFullInfo>() {
        @Override
        protected @NotNull ListEncoder<StreamFullInfo> getBuilder() {
            return EncoderFactory.STREAM_INFO_FULL;
        }

        @Override
        protected @NotNull ObjectConverter<StreamFullInfo> getConverter() {
            return ConverterFactory.STREAM_INFO_FULL;
        }
    };

    public static final ResultParser STREAM_PENDING_ENTRY = new ObjectListResultParser<StreamPendingEntry>() {
        @Override
        protected @NotNull ListEncoder<StreamPendingEntry> getBuilder() {
            return EncoderFactory.STREAM_PENDING_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingEntry> getConverter() {
            return ConverterFactory.STREAM_PENDING_ENTRY;
        }
    };

    public static final ResultParser STREAM_PENDING_SUMMARY = new ObjectListResultParser<StreamPendingSummary>() {
        @Override
        protected @NotNull ListEncoder<StreamPendingSummary> getBuilder() {
            return EncoderFactory.STREAM_PENDING_SUMMARY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingSummary> getConverter() {
            return ConverterFactory.STREAM_PENDING_SUMMARY;
        }
    };


    public static final ResultParser KEYED_STRING_LIST = new ObjectListResultParser<KeyValue<String, List<String>>>() {
        @Override
        protected @NotNull ListEncoder<KeyValue<String, List<String>>> getBuilder() {
            return EncoderFactory.KEYED_STRING_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<KeyValue<String, List<String>>> getConverter() {
            return ConverterFactory.KEYED_STRING_LIST;
        }
    };

    public static final ResultParser KEYED_TUPLE_LIST = new ObjectListResultParser<KeyValue<String, List<Tuple>>>() {
        @Override
        protected @NotNull ListEncoder<KeyValue<String, List<Tuple>>> getBuilder() {
            return EncoderFactory.KEYED_TUPLE_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<KeyValue<String, List<Tuple>>> getConverter() {
            return ConverterFactory.KEYED_TUPLE_LIST;
        }
    };


    public static final ResultParser STRING_SCAN_RESULT = new ObjectListResultParser<ScanResult<String>>() {
        @Override
        protected @NotNull ListEncoder<ScanResult<String>> getBuilder() {
            return EncoderFactory.STRING_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<String>> getConverter() {
            return ConverterFactory.STRING_SCAN_RESULT;
        }
    };

    public static final ResultParser TUPLE_SCAN_RESULT = new ObjectListResultParser<ScanResult<Tuple>>() {
        @Override
        protected @NotNull ListEncoder<ScanResult<Tuple>> getBuilder() {
            return EncoderFactory.TUPLE_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Tuple>> getConverter() {
            return ConverterFactory.TUPLE_SCAN_RESULT;
        }
    };

    public static final ResultParser ENTRY_SCAN_RESULT = new ObjectListResultParser<ScanResult<Map.Entry<String, String>>>() {
        @Override
        protected @NotNull ListEncoder<ScanResult<Map.Entry<String, String>>> getBuilder() {
            return EncoderFactory.ENTRY_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Map.Entry<String, String>>> getConverter() {
            return ConverterFactory.ENTRY_SCAN_RESULT;
        }
    };


    /* --------------------------------------------- RedisJSON --------------------------------------------- */

    public static final ResultParser JSON_OBJECT = new ListResultParser<>() {
        @Override
        protected @NotNull ListEncoder<Object> getBuilder() {
            return EncoderFactory.JSON_OBJECT;
        }

        @Override
        protected @NotNull IdentityConverter<Object> getConverter() {
            return ConverterFactory.OBJECT;
        }
    };


    /* --------------------------------------------- RediSearch --------------------------------------------- */

    public static final ResultParser AGGREGATION_RESULT = new ObjectListResultParser<AggregationResult>() {
        @Override
        protected @NotNull ListEncoder<AggregationResult> getBuilder() {
            return EncoderFactory.AGGREGATION_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<AggregationResult> getConverter() {
            return ConverterFactory.AGGREGATION_RESULT;
        }
    };


    /* ------------------------------------------------------------------------------------------ */

    private static abstract class ListResultParser<T, S> implements ResultParser {
        protected abstract @NotNull ListEncoder<T> getBuilder();
        protected abstract @NotNull SimpleConverter<T, S> getConverter();

        protected final @NotNull SimpleType<S> getType() {
            return getConverter().getSimpleType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public final @NotNull RedisListResult parse(@Nullable Object data, @NotNull RedisQuery query) {
            List<T> encoded = getBuilder().encode(data, query.getParams());
            List<S> converted = getConverter().convertList(encoded);
            return new RedisListResult(query, getType(), (List<Object>) converted);
        }
    }

    private static abstract class MapResultParser<T, S> implements ResultParser {
        protected abstract @NotNull MapEncoder<T> getBuilder();
        protected abstract @NotNull SimpleConverter<T, S> getConverter();

        protected final @NotNull SimpleType<S> getType() {
            return getConverter().getSimpleType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public final @NotNull RedisMapResult parse(@Nullable Object data, @NotNull RedisQuery query) {
            Map<String, T> encoded = getBuilder().encode(data, query.getParams());
            Map<String, S> converted = getConverter().convertMap(encoded);
            return new RedisMapResult(query, getType(), (Map<String, Object>) converted);
        }
    }

    private static abstract class ObjectListResultParser<T> implements ResultParser {
        protected abstract @NotNull ListEncoder<T> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        protected final @NotNull ObjectType<T> getType() {
            return getConverter().getObjectType();
        }

        @Override
        public final @NotNull RedisObjectResult parse(@Nullable Object data, @NotNull RedisQuery query) {
            List<T> encoded = getBuilder().encode(data, query.getParams());
            List<Map<String, Object>> converted = getConverter().convertList(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }

    private static abstract class ObjectMapResultParser<T> implements ResultParser {
        protected abstract @NotNull MapEncoder<T> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        protected final @NotNull ObjectType<T> getType() {
            return getConverter().getObjectType();
        }

        @Override
        public final @NotNull RedisObjectResult parse(@Nullable Object data, @NotNull RedisQuery query) {
            Map<String, T> encoded = getBuilder().encode(data, query.getParams());
            List<Map<String, Object>> converted = getConverter().convertMap(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }
}
