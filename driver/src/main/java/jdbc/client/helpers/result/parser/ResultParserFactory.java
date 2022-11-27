package jdbc.client.helpers.result.parser;

import jdbc.client.helpers.result.parser.builder.BuilderWrapperFactory;
import jdbc.client.helpers.result.parser.builder.ListBuilderWrapper;
import jdbc.client.helpers.result.parser.builder.MapBuilderWrapper;
import jdbc.client.helpers.result.parser.converter.ConverterFactory;
import jdbc.client.helpers.result.parser.converter.ObjectConverter;
import jdbc.client.helpers.result.parser.converter.SimpleConverter;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Module;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;

import java.util.List;
import java.util.Map;

public class ResultParserFactory {

    public static final ResultParser RESULT = new ListResultParser<>() {
        @Override
        protected @NotNull ListBuilderWrapper<Object> getBuilder() {
            return BuilderWrapperFactory.RESULT;
        }

        @Override
        protected @NotNull SimpleConverter<Object, Object> getConverter() {
            return ConverterFactory.OBJECT;
        }
    };

    public static final ResultParser LONG = new ListResultParser<Long, Long>() {
        @Override
        protected @NotNull ListBuilderWrapper<Long> getBuilder() {
            return BuilderWrapperFactory.LONG_RESULT;
        }

        @Override
        protected @NotNull SimpleConverter<Long, Long> getConverter() {
            return ConverterFactory.LONG;
        }
    };

    public static final ResultParser DOUBLE = new ListResultParser<Double, Double>() {
        @Override
        protected @NotNull ListBuilderWrapper<Double> getBuilder() {
            return BuilderWrapperFactory.DOUBLE_RESULT;
        }

        @Override
        protected @NotNull SimpleConverter<Double, Double> getConverter() {
            return ConverterFactory.DOUBLE;
        }
    };

    public static final ResultParser BOOLEAN = new ListResultParser<Boolean, Boolean>() {
        @Override
        protected @NotNull ListBuilderWrapper<Boolean> getBuilder() {
            return BuilderWrapperFactory.BOOLEAN_RESULT;
        }

        @Override
        protected @NotNull SimpleConverter<Boolean, Boolean> getConverter() {
            return ConverterFactory.BOOLEAN;
        }
    };

    public static final ResultParser BYTE_ARRAY = new ListResultParser<byte[], byte[]>() {
        @Override
        protected @NotNull ListBuilderWrapper<byte[]> getBuilder() {
            return BuilderWrapperFactory.BYTE_ARRAY_RESULT;
        }

        @Override
        protected @NotNull SimpleConverter<byte[], byte[]> getConverter() {
            return ConverterFactory.BYTE_ARRAY;
        }
    };

    public static final ResultParser STRING_MAP = new MapResultParser<String, String>() {
        @Override
        protected @NotNull MapBuilderWrapper<String> getBuilder() {
            return BuilderWrapperFactory.STRING_MAP;
        }

        @Override
        protected @NotNull SimpleConverter<String, String> getConverter() {
            return ConverterFactory.STRING;
        }
    };

    public static final ResultParser TUPLE = new ObjectListResultParser<Tuple>() {
        @Override
        protected @NotNull ListBuilderWrapper<Tuple> getBuilder() {
            return BuilderWrapperFactory.TUPLE_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<Tuple> getConverter() {
            return ConverterFactory.TUPLE;
        }
    };

    public static final ResultParser KEYED_LIST_ELEMENT = new ObjectListResultParser<KeyedListElement>() {
        @Override
        protected @NotNull ListBuilderWrapper<KeyedListElement> getBuilder() {
            return BuilderWrapperFactory.KEYED_LIST_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedListElement> getConverter() {
            return ConverterFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final ResultParser KEYED_ZSET_ELEMENT = new ObjectListResultParser<KeyedZSetElement>() {
        @Override
        protected @NotNull ListBuilderWrapper<KeyedZSetElement> getBuilder() {
            return BuilderWrapperFactory.KEYED_ZSET_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedZSetElement> getConverter() {
            return ConverterFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final ResultParser GEO_COORDINATE = new ObjectListResultParser<GeoCoordinate>() {
        @Override
        protected @NotNull ListBuilderWrapper<GeoCoordinate> getBuilder() {
            return BuilderWrapperFactory.GEO_COORDINATE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoCoordinate> getConverter() {
            return ConverterFactory.GEO_COORDINATE;
        }
    };

    public static final ResultParser GEORADIUS_RESPONSE = new ObjectListResultParser<GeoRadiusResponse>() {
        @Override
        protected @NotNull ListBuilderWrapper<GeoRadiusResponse> getBuilder() {
            return BuilderWrapperFactory.GEORADIUS_RESPONSE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoRadiusResponse> getConverter() {
            return ConverterFactory.GEORADIUS_RESPONSE;
        }
    };

    public static final ResultParser MODULE = new ObjectListResultParser<Module>() {
        @Override
        protected @NotNull ListBuilderWrapper<Module> getBuilder() {
            return BuilderWrapperFactory.MODULE;
        }

        @Override
        protected @NotNull ObjectConverter<Module> getConverter() {
            return ConverterFactory.MODULE;
        }
    };

    public static final ResultParser ACCESS_CONTROL_USER = new ObjectListResultParser<AccessControlUser>() {
        @Override
        protected @NotNull ListBuilderWrapper<AccessControlUser> getBuilder() {
            return BuilderWrapperFactory.ACCESS_CONTROL_USER;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlUser> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ResultParser ACCESS_CONTROL_LOG_ENTRY = new ObjectListResultParser<AccessControlLogEntry>() {
        @Override
        protected @NotNull ListBuilderWrapper<AccessControlLogEntry> getBuilder() {
            return BuilderWrapperFactory.ACCESS_CONTROL_LOG_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlLogEntry> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_LOG_ENTRY;
        }
    };

    public static final ResultParser COMMAND_DOCUMENT = new ObjectMapResultParser<CommandDocument>() {
        @Override
        protected @NotNull MapBuilderWrapper<CommandDocument> getBuilder() {
            return BuilderWrapperFactory.COMMAND_DOCUMENT;
        }

        @Override
        protected @NotNull ObjectConverter<CommandDocument> getConverter() {
            return ConverterFactory.COMMAND_DOCUMENT;
        }
    };

    public static final ResultParser COMMAND_INFO = new ObjectMapResultParser<CommandInfo>() {
        @Override
        protected @NotNull MapBuilderWrapper<CommandInfo> getBuilder() {
            return BuilderWrapperFactory.COMMAND_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<CommandInfo> getConverter() {
            return ConverterFactory.COMMAND_INFO;
        }
    };

    public static final ResultParser FUNCTION_STATS = new ObjectListResultParser<FunctionStats>() {
        @Override
        protected @NotNull ListBuilderWrapper<FunctionStats> getBuilder() {
            return BuilderWrapperFactory.FUNCTION_STATS;
        }

        @Override
        protected @NotNull ObjectConverter<FunctionStats> getConverter() {
            return ConverterFactory.FUNCTION_STATS;
        }
    };

    public static final ResultParser LIBRARY_INFO = new ObjectListResultParser<LibraryInfo>() {
        @Override
        protected @NotNull ListBuilderWrapper<LibraryInfo> getBuilder() {
            return BuilderWrapperFactory.LIBRARY_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<LibraryInfo> getConverter() {
            return ConverterFactory.LIBRARY_INFO;
        }
    };

    public static final ResultParser SLOW_LOG = new ObjectListResultParser<Slowlog>() {
        @Override
        protected @NotNull ListBuilderWrapper<Slowlog> getBuilder() {
            return BuilderWrapperFactory.SLOW_LOG;
        }

        @Override
        protected @NotNull ObjectConverter<Slowlog> getConverter() {
            return ConverterFactory.SLOW_LOG;
        }
    };

    public static final ResultParser STREAM_ENTRY_ID = new ListResultParser<StreamEntryID, String>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamEntryID> getBuilder() {
            return BuilderWrapperFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull SimpleConverter<StreamEntryID, String> getConverter() {
            return ConverterFactory.STREAM_ENTRY_ID;
        }
    };

    public static final ResultParser STREAM_ENTRY = new ObjectListResultParser<StreamEntry>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamEntry> getBuilder() {
            return BuilderWrapperFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamEntry> getConverter() {
            return ConverterFactory.STREAM_ENTRY;
        }
    };

    public static final ResultParser STREAM_READ = new ObjectListResultParser<Map.Entry<String, List<StreamEntry>>>() {
        @Override
        protected @NotNull ListBuilderWrapper<Map.Entry<String, List<StreamEntry>>> getBuilder() {
            return BuilderWrapperFactory.STREAM_READ_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<Map.Entry<String, List<StreamEntry>>> getConverter() {
            return ConverterFactory.STREAM_READ_ENTRY;
        }
    };

    public static final ResultParser STREAM_CONSUMER_INFO = new ObjectListResultParser<StreamConsumersInfo>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamConsumersInfo> getBuilder() {
            return BuilderWrapperFactory.STREAM_CONSUMER_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamConsumersInfo> getConverter() {
            return ConverterFactory.STREAM_CONSUMER_INFO;
        }
    };

    public static final ResultParser STREAM_GROUP_INFO = new ObjectListResultParser<StreamGroupInfo>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamGroupInfo> getBuilder() {
            return BuilderWrapperFactory.STREAM_GROUP_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamGroupInfo> getConverter() {
            return ConverterFactory.STREAM_GROUP_INFO;
        }
    };

    public static final ResultParser STREAM_INFO = new ObjectListResultParser<StreamInfo>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamInfo> getBuilder() {
            return BuilderWrapperFactory.STREAM_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamInfo> getConverter() {
            return ConverterFactory.STREAM_INFO;
        }
    };

    public static final ResultParser STREAM_INFO_FULL = new ObjectListResultParser<StreamFullInfo>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamFullInfo> getBuilder() {
            return BuilderWrapperFactory.STREAM_INFO_FULL;
        }

        @Override
        protected @NotNull ObjectConverter<StreamFullInfo> getConverter() {
            return ConverterFactory.STREAM_INFO_FULL;
        }
    };

    public static final ResultParser STREAM_PENDING_ENTRY = new ObjectListResultParser<StreamPendingEntry>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamPendingEntry> getBuilder() {
            return BuilderWrapperFactory.STREAM_PENDING_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingEntry> getConverter() {
            return ConverterFactory.STREAM_PENDING_ENTRY;
        }
    };

    public static final ResultParser STREAM_PENDING_SUMMARY = new ObjectListResultParser<StreamPendingSummary>() {
        @Override
        protected @NotNull ListBuilderWrapper<StreamPendingSummary> getBuilder() {
            return BuilderWrapperFactory.STREAM_PENDING_SUMMARY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingSummary> getConverter() {
            return ConverterFactory.STREAM_PENDING_SUMMARY;
        }
    };

    public static final ResultParser STRING_SCAN_RESULT = new ObjectListResultParser<ScanResult<String>>() {
        @Override
        protected @NotNull ListBuilderWrapper<ScanResult<String>> getBuilder() {
            return BuilderWrapperFactory.STRING_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<String>> getConverter() {
            return ConverterFactory.STRING_SCAN_RESULT;
        }
    };

    public static final ResultParser TUPLE_SCAN_RESULT = new ObjectListResultParser<ScanResult<Tuple>>() {
        @Override
        protected @NotNull ListBuilderWrapper<ScanResult<Tuple>> getBuilder() {
            return BuilderWrapperFactory.TUPLE_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Tuple>> getConverter() {
            return ConverterFactory.TUPLE_SCAN_RESULT;
        }
    };

    public static final ResultParser ENTRY_SCAN_RESULT = new ObjectListResultParser<ScanResult<Map.Entry<String, String>>>() {
        @Override
        protected @NotNull ListBuilderWrapper<ScanResult<Map.Entry<String, String>>> getBuilder() {
            return BuilderWrapperFactory.ENTRY_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Map.Entry<String, String>>> getConverter() {
            return ConverterFactory.ENTRY_SCAN_RESULT;
        }
    };


    private static abstract class ListResultParser<T, S> implements ResultParser {
        protected abstract @NotNull ListBuilderWrapper<T> getBuilder();
        protected abstract @NotNull SimpleConverter<T, S> getConverter();

        protected final @NotNull SimpleType<S> getType() {
            return getConverter().getSimpleType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public final @NotNull RedisListResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<S> converted = getConverter().convertList(encoded);
            return new RedisListResult(query, getType(), (List<Object>) converted);
        }
    }

    private static abstract class MapResultParser<T, S> implements ResultParser {
        protected abstract @NotNull MapBuilderWrapper<T> getBuilder();
        protected abstract @NotNull SimpleConverter<T, S> getConverter();

        protected final @NotNull SimpleType<S> getType() {
            return getConverter().getSimpleType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public final @NotNull RedisMapResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            Map<String, T> encoded = getBuilder().build(data);
            Map<String, S> converted = getConverter().convertMap(encoded);
            return new RedisMapResult(query, getType(), (Map<String, Object>) converted);
        }
    }

    private static abstract class ObjectListResultParser<T> implements ResultParser {
        protected abstract @NotNull ListBuilderWrapper<T> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        protected final @NotNull ObjectType<T> getType() {
            return getConverter().getObjectType();
        }

        @Override
        public final @NotNull RedisObjectResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<Map<String, Object>> converted = getConverter().convertList(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }

    private static abstract class ObjectMapResultParser<T> implements ResultParser {
        protected abstract @NotNull MapBuilderWrapper<T> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        protected final @NotNull ObjectType<T> getType() {
            return getConverter().getObjectType();
        }

        @Override
        public final @NotNull RedisObjectResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            Map<String, T> encoded = getBuilder().build(data);
            List<Map<String, Object>> converted = getConverter().convertMap(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }
}
