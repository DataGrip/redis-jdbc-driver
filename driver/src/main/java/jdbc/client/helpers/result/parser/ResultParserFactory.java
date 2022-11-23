package jdbc.client.helpers.result.parser;

import jdbc.client.helpers.result.parser.builder.BuilderWrapper;
import jdbc.client.helpers.result.parser.builder.BuilderWrapperFactory;
import jdbc.client.helpers.result.parser.converter.ConverterFactory;
import jdbc.client.helpers.result.parser.converter.IdentityConverter;
import jdbc.client.helpers.result.parser.converter.ObjectConverter;
import jdbc.client.helpers.result.parser.converter.SimpleConverter;
import jdbc.client.helpers.result.parser.type.TypeFactory;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.ObjectType;
import jdbc.client.structures.result.RedisListResult;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.client.structures.result.RedisObjectResult;
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
        protected @NotNull String getType() {
            // TODO: think about type here
            return TypeFactory.STRING;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Object>> getBuilder() {
            return BuilderWrapperFactory.RESULT;
        }
    };

    public static final ResultParser LONG = new ListResultParser<Long>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.LONG;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Long>> getBuilder() {
            return BuilderWrapperFactory.LONG_RESULT;
        }
    };

    public static final ResultParser DOUBLE = new ListResultParser<Double>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.DOUBLE;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Double>> getBuilder() {
            return BuilderWrapperFactory.DOUBLE_RESULT;
        }
    };

    public static final ResultParser BOOLEAN = new ListResultParser<Boolean>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.BOOLEAN;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Boolean>> getBuilder() {
            return BuilderWrapperFactory.BOOLEAN_RESULT;
        }
    };

    public static final ResultParser BYTE_ARRAY = new ListResultParser<byte[]>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.BYTE_ARRAY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<byte[]>> getBuilder() {
            return BuilderWrapperFactory.BYTE_ARRAY_RESULT;
        }
    };

    public static final ResultParser STRING_MAP = new MapResultParser<String>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.STRING;
        }

        @Override
        protected @NotNull BuilderWrapper<Map<String, String>> getBuilder() {
            return BuilderWrapperFactory.STRING_MAP;
        }
    };

    public static final ResultParser TUPLE = new ObjectListResultParser<Tuple>() {
        @Override
        protected @NotNull ObjectType<Tuple> getType() {
            return TypeFactory.TUPLE;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Tuple>> getBuilder() {
            return BuilderWrapperFactory.TUPLE_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<Tuple> getConverter() {
            return ConverterFactory.TUPLE;
        }
    };

    public static final ResultParser KEYED_LIST_ELEMENT = new ObjectListResultParser<KeyedListElement>() {
        @Override
        protected @NotNull ObjectType<KeyedListElement> getType() {
            return TypeFactory.KEYED_LIST_ELEMENT;
        }

        @Override
        protected @NotNull BuilderWrapper<List<KeyedListElement>> getBuilder() {
            return BuilderWrapperFactory.KEYED_LIST_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedListElement> getConverter() {
            return ConverterFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final ResultParser KEYED_ZSET_ELEMENT = new ObjectListResultParser<KeyedZSetElement>() {
        @Override
        protected @NotNull ObjectType<KeyedZSetElement> getType() {
            return TypeFactory.KEYED_ZSET_ELEMENT;
        }

        @Override
        protected @NotNull BuilderWrapper<List<KeyedZSetElement>> getBuilder() {
            return BuilderWrapperFactory.KEYED_ZSET_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedZSetElement> getConverter() {
            return ConverterFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final ResultParser GEO_COORDINATE = new ObjectListResultParser<GeoCoordinate>() {
        @Override
        protected @NotNull ObjectType<GeoCoordinate> getType() {
            return TypeFactory.GEO_COORDINATE;
        }

        @Override
        protected @NotNull BuilderWrapper<List<GeoCoordinate>> getBuilder() {
            return BuilderWrapperFactory.GEO_COORDINATE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoCoordinate> getConverter() {
            return ConverterFactory.GEO_COORDINATE;
        }
    };

    public static final ResultParser GEORADIUS_RESPONSE = new ObjectListResultParser<GeoRadiusResponse>() {
        @Override
        protected @NotNull ObjectType<GeoRadiusResponse> getType() {
            return TypeFactory.GEORADIUS_RESPONSE;
        }

        @Override
        protected @NotNull BuilderWrapper<List<GeoRadiusResponse>> getBuilder() {
            return BuilderWrapperFactory.GEORADIUS_RESPONSE;
        }

        @Override
        protected @NotNull ObjectConverter<GeoRadiusResponse> getConverter() {
            return ConverterFactory.GEORADIUS_RESPONSE;
        }
    };

    public static final ResultParser MODULE = new ObjectListResultParser<Module>() {
        @Override
        protected @NotNull ObjectType<Module> getType() {
            return TypeFactory.MODULE;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Module>> getBuilder() {
            return BuilderWrapperFactory.MODULE;
        }

        @Override
        protected @NotNull ObjectConverter<Module> getConverter() {
            return ConverterFactory.MODULE;
        }
    };

    public static final ResultParser ACCESS_CONTROL_USER = new ObjectListResultParser<AccessControlUser>() {
        @Override
        protected @NotNull ObjectType<AccessControlUser> getType() {
            return TypeFactory.ACCESS_CONTROL_USER;
        }

        @Override
        protected @NotNull BuilderWrapper<List<AccessControlUser>> getBuilder() {
            return BuilderWrapperFactory.ACCESS_CONTROL_USER;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlUser> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ResultParser ACCESS_CONTROL_LOG_ENTRY = new ObjectListResultParser<AccessControlLogEntry>() {
        @Override
        protected @NotNull ObjectType<AccessControlLogEntry> getType() {
            return TypeFactory.ACCESS_CONTROL_LOG_ENTRY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<AccessControlLogEntry>> getBuilder() {
            return BuilderWrapperFactory.ACCESS_CONTROL_LOG_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlLogEntry> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_LOG_ENTRY;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser COMMAND_DOCUMENT = new ObjectMapResultParser<CommandDocument>() {
        @Override
        protected @NotNull ObjectType<CommandDocument> getType() {
            return TypeFactory.COMMAND_DOCUMENT;
        }

        @Override
        protected @NotNull BuilderWrapper<Map<String, CommandDocument>> getBuilder() {
            return BuilderWrapperFactory.COMMAND_DOCUMENT;
        }

        @Override
        protected @NotNull ObjectConverter<CommandDocument> getConverter() {
            return ConverterFactory.COMMAND_DOCUMENT;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser COMMAND_INFO = new ObjectMapResultParser<CommandInfo>() {
        @Override
        protected @NotNull ObjectType<CommandInfo> getType() {
            return TypeFactory.COMMAND_INFO;
        }

        @Override
        protected @NotNull BuilderWrapper<Map<String, CommandInfo>> getBuilder() {
            return BuilderWrapperFactory.COMMAND_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<CommandInfo> getConverter() {
            return ConverterFactory.COMMAND_INFO;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser FUNCTION_STATS = new ObjectListResultParser<FunctionStats>() {
        @Override
        protected @NotNull ObjectType<FunctionStats> getType() {
            return TypeFactory.FUNCTION_STATS;
        }

        @Override
        protected @NotNull BuilderWrapper<List<FunctionStats>> getBuilder() {
            return BuilderWrapperFactory.FUNCTION_STATS;
        }

        @Override
        protected @NotNull ObjectConverter<FunctionStats> getConverter() {
            return ConverterFactory.FUNCTION_STATS;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser LIBRARY_INFO = new ObjectListResultParser<LibraryInfo>() {

        @Override
        protected @NotNull ObjectType<LibraryInfo> getType() {
            return TypeFactory.LIBRARY_INFO;
        }

        @Override
        protected @NotNull BuilderWrapper<List<LibraryInfo>> getBuilder() {
            return BuilderWrapperFactory.LIBRARY_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<LibraryInfo> getConverter() {
            return ConverterFactory.LIBRARY_INFO;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser SLOW_LOG = new ObjectListResultParser<Slowlog>() {
        @Override
        protected @NotNull ObjectType<Slowlog> getType() {
            return TypeFactory.SLOW_LOG;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Slowlog>> getBuilder() {
            return BuilderWrapperFactory.SLOW_LOG;
        }

        @Override
        protected @NotNull ObjectConverter<Slowlog> getConverter() {
            return ConverterFactory.SLOW_LOG;
        }
    };

    public static final ResultParser STREAM_ENTRY_ID = new ListResultParser<StreamEntryID>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamEntryID>> getBuilder() {
            return BuilderWrapperFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull SimpleConverter<StreamEntryID> getConverter() {
            return ConverterFactory.STREAM_ENTRY_ID;
        }
    };

    public static final ResultParser STREAM_ENTRY = new ObjectListResultParser<StreamEntry>() {
        @Override
        protected @NotNull ObjectType<StreamEntry> getType() {
            return TypeFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamEntry>> getBuilder() {
            return BuilderWrapperFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamEntry> getConverter() {
            return ConverterFactory.STREAM_ENTRY;
        }
    };

    public static final ResultParser STREAM_READ = new ObjectListResultParser<Map.Entry<String, List<StreamEntry>>>() {
        @Override
        protected @NotNull ObjectType<Map.Entry<String, List<StreamEntry>>> getType() {
            return TypeFactory.STREAM_READ_ENTRY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<Map.Entry<String, List<StreamEntry>>>> getBuilder() {
            return BuilderWrapperFactory.STREAM_READ_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<Map.Entry<String, List<StreamEntry>>> getConverter() {
            return ConverterFactory.STREAM_READ_ENTRY;
        }
    };

    public static final ResultParser STREAM_GROUP_INFO = new ObjectListResultParser<StreamGroupInfo>() {
        @Override
        protected @NotNull ObjectType<StreamGroupInfo> getType() {
            return TypeFactory.STREAM_GROUP_INFO;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamGroupInfo>> getBuilder() {
            return BuilderWrapperFactory.STREAM_GROUP_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamGroupInfo> getConverter() {
            return ConverterFactory.STREAM_GROUP_INFO;
        }
    };

    public static final ResultParser STREAM_CONSUMERS_INFO = new ObjectListResultParser<StreamConsumersInfo>() {
        @Override
        protected @NotNull ObjectType<StreamConsumersInfo> getType() {
            return TypeFactory.STREAM_CONSUMERS_INFO;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamConsumersInfo>> getBuilder() {
            return BuilderWrapperFactory.STREAM_CONSUMERS_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamConsumersInfo> getConverter() {
            return ConverterFactory.STREAM_CONSUMERS_INFO;
        }
    };

    public static final ResultParser STREAM_INFO = new ObjectListResultParser<StreamInfo>() {
        @Override
        protected @NotNull ObjectType<StreamInfo> getType() {
            return TypeFactory.STREAM_INFO;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamInfo>> getBuilder() {
            return BuilderWrapperFactory.STREAM_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamInfo> getConverter() {
            return ConverterFactory.STREAM_INFO;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser STREAM_INFO_FULL = new ObjectListResultParser<StreamFullInfo>() {
        @Override
        protected @NotNull ObjectType<StreamFullInfo> getType() {
            return TypeFactory.STREAM_INFO_FULL;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamFullInfo>> getBuilder() {
            return BuilderWrapperFactory.STREAM_INFO_FULL;
        }

        @Override
        protected @NotNull ObjectConverter<StreamFullInfo> getConverter() {
            return ConverterFactory.STREAM_INFO_FULL;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser STREAM_PENDING_ENTRY = new ObjectListResultParser<StreamPendingEntry>() {
        @Override
        protected @NotNull ObjectType<StreamPendingEntry> getType() {
            return TypeFactory.STREAM_PENDING_ENTRY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamPendingEntry>> getBuilder() {
            return BuilderWrapperFactory.STREAM_PENDING_ENTRY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingEntry> getConverter() {
            return ConverterFactory.STREAM_PENDING_ENTRY;
        }
    };

    // TODO (new) Helper + Builder + Type + Converter
    public static final ResultParser STREAM_PENDING_SUMMARY = new ObjectListResultParser<StreamPendingSummary>() {
        @Override
        protected @NotNull ObjectType<StreamPendingSummary> getType() {
            return TypeFactory.STREAM_PENDING_SUMMARY;
        }

        @Override
        protected @NotNull BuilderWrapper<List<StreamPendingSummary>> getBuilder() {
            return BuilderWrapperFactory.STREAM_PENDING_SUMMARY;
        }

        @Override
        protected @NotNull ObjectConverter<StreamPendingSummary> getConverter() {
            return ConverterFactory.STREAM_PENDING_SUMMARY;
        }
    };

    public static final ResultParser STRING_SCAN_RESULT = new ObjectListResultParser<ScanResult<String>>() {
        @Override
        protected @NotNull ObjectType<ScanResult<String>> getType() {
            return TypeFactory.STRING_SCAN_RESULT;
        }

        @Override
        protected @NotNull BuilderWrapper<List<ScanResult<String>>> getBuilder() {
            return BuilderWrapperFactory.STRING_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<String>> getConverter() {
            return ConverterFactory.STRING_SCAN_RESULT;
        }
    };

    public static final ResultParser TUPLE_SCAN_RESULT = new ObjectListResultParser<ScanResult<Tuple>>() {
        @Override
        protected @NotNull ObjectType<ScanResult<Tuple>> getType() {
            return TypeFactory.TUPLE_SCAN_RESULT;
        }

        @Override
        protected @NotNull BuilderWrapper<List<ScanResult<Tuple>>> getBuilder() {
            return BuilderWrapperFactory.TUPLE_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Tuple>> getConverter() {
            return ConverterFactory.TUPLE_SCAN_RESULT;
        }
    };

    public static final ResultParser ENTRY_SCAN_RESULT = new ObjectListResultParser<ScanResult<Map.Entry<String, String>>>() {
        @Override
        protected @NotNull ObjectType<ScanResult<Map.Entry<String, String>>> getType() {
            return TypeFactory.ENTRY_SCAN_RESULT;
        }

        @Override
        protected @NotNull BuilderWrapper<List<ScanResult<Map.Entry<String, String>>>> getBuilder() {
            return BuilderWrapperFactory.ENTRY_SCAN_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<Map.Entry<String, String>>> getConverter() {
            return ConverterFactory.ENTRY_SCAN_RESULT;
        }
    };


    private static abstract class ListResultParser<T> implements ResultParser {
        protected abstract @NotNull String getType();
        protected abstract @NotNull BuilderWrapper<List<T>> getBuilder();
        protected @NotNull SimpleConverter<T> getConverter() {
            return new IdentityConverter<>();
        }

        @Override
        public final @NotNull RedisListResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<Object> converted = getConverter().convert(encoded);
            return new RedisListResult(query, getType(), converted);
        }
    }

    private static abstract class MapResultParser<T> implements ResultParser {
        protected abstract @NotNull String getType();
        protected abstract @NotNull BuilderWrapper<Map<String, T>> getBuilder();
        protected @NotNull SimpleConverter<T> getConverter() {
            return new IdentityConverter<>();
        }

        @Override
        public final @NotNull RedisMapResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            Map<String, T> encoded = getBuilder().build(data);
            Map<String, Object> converted = getConverter().convert(encoded);
            return new RedisMapResult(query, getType(), converted);
        }
    }

    private static abstract class ObjectListResultParser<T> implements ResultParser {
        protected abstract @NotNull ObjectType<T> getType();
        protected abstract @NotNull BuilderWrapper<List<T>> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        @Override
        public final @NotNull RedisObjectResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<Map<String, Object>> converted = getConverter().convert(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }

    private static abstract class ObjectMapResultParser<T> implements ResultParser {
        protected abstract @NotNull ObjectType<T> getType();
        protected abstract @NotNull BuilderWrapper<Map<String, T>> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        @Override
        public final @NotNull RedisObjectResult parse(@NotNull RedisQuery query, @Nullable Object data) {
            Map<String, T> encoded = getBuilder().build(data);
            List<Map<String, Object>> converted = getConverter().convert(encoded);
            return new RedisObjectResult(query, getType(), converted);
        }
    }
}
