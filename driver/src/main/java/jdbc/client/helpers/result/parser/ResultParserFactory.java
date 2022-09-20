package jdbc.client.helpers.result.parser;

import jdbc.client.helpers.result.parser.builder.BuilderFactoryEx;
import jdbc.client.helpers.result.parser.converter.ConverterFactory;
import jdbc.client.helpers.result.parser.converter.ObjectConverter;
import jdbc.client.helpers.result.parser.converter.SimpleConverter;
import jdbc.client.helpers.result.parser.type.TypeFactory;
import jdbc.client.helpers.result.parser.wrapper.Wrapper;
import jdbc.client.structures.result.RedisListResult;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.client.structures.result.RedisObjectResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;

import java.util.List;
import java.util.Map;

public class ResultParserFactory {

    public static final ResultParser RESULT = new ListResultParser<>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.STRING;
        }

        @Override
        protected @NotNull Builder<List<Object>> getBuilder() {
            return BuilderFactoryEx.RESULT;
        }
    };

    public static final ResultParser LONG = new ListResultParser<Long>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.LONG;
        }

        @Override
        protected @NotNull Builder<List<Long>> getBuilder() {
            return BuilderFactoryEx.LONG_RESULT;
        }
    };

    public static final ResultParser DOUBLE = new ListResultParser<Double>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.DOUBLE;
        }

        @Override
        protected @NotNull Builder<List<Double>> getBuilder() {
            return BuilderFactoryEx.DOUBLE_RESULT;
        }
    };

    public static final ResultParser BOOLEAN = new ListResultParser<Boolean>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.BOOLEAN;
        }

        @Override
        protected @NotNull Builder<List<Boolean>> getBuilder() {
            return BuilderFactoryEx.BOOLEAN_RESULT;
        }
    };

    public static final ResultParser BYTE_ARRAY = new ListResultParser<byte[]>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.ARRAY;
        }

        @Override
        protected @NotNull Builder<List<byte[]>> getBuilder() {
            return BuilderFactoryEx.BYTE_ARRAY_RESULT;
        }
    };

    public static final ResultParser STRING_MAP = new MapResultParser<String>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.STRING;
        }

        @Override
        protected @NotNull Builder<Map<String, String>> getBuilder() {
            return BuilderFactory.STRING_MAP;
        }
    };

    public static final ResultParser TUPLE = new ObjectResultParser<Tuple>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.TUPLE;
        }

        @Override
        protected @NotNull Builder<List<Tuple>> getBuilder() {
            return BuilderFactoryEx.TUPLE_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<Tuple> getConverter() {
            return ConverterFactory.TUPLE;
        }
    };

    public static final ResultParser KEYED_LIST_ELEMENT = new ObjectResultParser<KeyedListElement>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.KEYED_LIST_ELEMENT;
        }

        @Override
        protected @NotNull Builder<List<KeyedListElement>> getBuilder() {
            return BuilderFactoryEx.KEYED_LIST_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedListElement> getConverter() {
            return ConverterFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final ResultParser KEYED_ZSET_ELEMENT = new ObjectResultParser<KeyedZSetElement>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.KEYED_ZSET_ELEMENT;
        }

        @Override
        protected @NotNull Builder<List<KeyedZSetElement>> getBuilder() {
            return BuilderFactoryEx.KEYED_ZSET_ELEMENT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<KeyedZSetElement> getConverter() {
            return ConverterFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final ResultParser GEO_COORDINATE = new ObjectResultParser<GeoCoordinate>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.GEO_COORDINATE;
        }

        @Override
        protected @NotNull Builder<List<GeoCoordinate>> getBuilder() {
            return BuilderFactory.GEO_COORDINATE_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<GeoCoordinate> getConverter() {
            return ConverterFactory.GEO_COORDINATE;
        }
    };

    public static final ResultParser GEORADIUS_RESPONSE = new ObjectResultParser<GeoRadiusResponse>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.GEORADIUS_RESPONSE;
        }

        @Override
        protected @NotNull Builder<List<GeoRadiusResponse>> getBuilder() {
            return BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<GeoRadiusResponse> getConverter() {
            return ConverterFactory.GEORADIUS_RESPONSE;
        }
    };

    public static final ResultParser MODULE = new ObjectResultParser<Module>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.MODULE;
        }

        @Override
        protected @NotNull Builder<List<Module>> getBuilder() {
            return BuilderFactory.MODULE_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<Module> getConverter() {
            return ConverterFactory.MODULE;
        }
    };

    public static final ResultParser ACCESS_CONTROL_USER = new ObjectResultParser<AccessControlUser>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.ACCESS_CONTROL_USER;
        }

        @Override
        protected @NotNull Builder<List<AccessControlUser>> getBuilder() {
            return BuilderFactoryEx.ACCESS_CONTROL_USER_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlUser> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final ResultParser ACCESS_CONTROL_LOG_ENTRY = new ObjectResultParser<AccessControlLogEntry>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.ACCESS_CONTROL_LOG_ENTRY;
        }

        @Override
        protected @NotNull Builder<List<AccessControlLogEntry>> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<AccessControlLogEntry> getConverter() {
            return ConverterFactory.ACCESS_CONTROL_LOG_ENTRY;
        }
    };

    public static final ResultParser STREAM_ENTRY_ID = new ListResultParser<StreamEntryID>() {
        @Override
        protected @NotNull String getType() {
            return TypeFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected @NotNull Builder<List<StreamEntryID>> getBuilder() {
            return BuilderFactoryEx.STREAM_ENTRY_ID_RESULT;
        }

        @Override
        protected SimpleConverter<StreamEntryID> getConverter() {
            return ConverterFactory.STREAM_ENTRY_ID;
        }
    };

    public static final ResultParser STREAM_ENTRY = new ObjectResultParser<StreamEntry>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STREAM_ENTRY;
        }

        @Override
        protected @NotNull Builder<List<StreamEntry>> getBuilder() {
            return BuilderFactoryEx.STREAM_ENTRY_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<StreamEntry> getConverter() {
            return ConverterFactory.STREAM_ENTRY;
        }
    };

    public static final ResultParser STREAM_READ = new ObjectResultParser<Map.Entry<String, List<StreamEntry>>>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STREAM_READ;
        }

        @Override
        protected @NotNull Builder<List<Map.Entry<String, List<StreamEntry>>>> getBuilder() {
            return BuilderFactory.STREAM_READ_RESPONSE;
        }

        @Override
        protected @NotNull ObjectConverter<Map.Entry<String, List<StreamEntry>>> getConverter() {
            return ConverterFactory.STREAM_READ;
        }
    };

    public static final ResultParser STREAM_INFO = new ObjectResultParser<StreamInfo>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STREAM_INFO;
        }

        @Override
        protected @NotNull Builder<List<StreamInfo>> getBuilder() {
            return BuilderFactoryEx.STREAM_INFO;
        }

        @Override
        protected @NotNull ObjectConverter<StreamInfo> getConverter() {
            return ConverterFactory.STREAM_INFO;
        }
    };

    public static final ResultParser STREAM_GROUP_INFO = new ObjectResultParser<StreamGroupInfo>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STREAM_GROUP_INFO;
        }

        @Override
        protected @NotNull Builder<List<StreamGroupInfo>> getBuilder() {
            return BuilderFactory.STREAM_GROUP_INFO_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<StreamGroupInfo> getConverter() {
            return ConverterFactory.STREAM_GROUP_INFO;
        }
    };

    public static final ResultParser STREAM_CONSUMERS_INFO = new ObjectResultParser<StreamConsumersInfo>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STREAM_CONSUMERS_INFO;
        }

        @Override
        protected @NotNull Builder<List<StreamConsumersInfo>> getBuilder() {
            return BuilderFactory.STREAM_CONSUMERS_INFO_LIST;
        }

        @Override
        protected @NotNull ObjectConverter<StreamConsumersInfo> getConverter() {
            return ConverterFactory.STREAM_CONSUMERS_INFO;
        }
    };

    public static final ResultParser STRING_SCAN_RESULT = new ObjectResultParser<ScanResult<String>>() {
        @Override
        protected @NotNull Map<String, String> getType() {
            return TypeFactory.STRING_SCAN_RESULT;
        }

        @Override
        protected @NotNull Builder<List<ScanResult<String>>> getBuilder() {
            return BuilderFactoryEx.STRING_SCAN_RESULT_RESULT;
        }

        @Override
        protected @NotNull ObjectConverter<ScanResult<String>> getConverter() {
            return ConverterFactory.STRING_SCAN_RESULT;
        }
    };


    private static abstract class ListResultParser<T> implements ResultParser {
        protected abstract @NotNull String getType();
        protected abstract @NotNull Builder<List<T>> getBuilder();
        protected @Nullable SimpleConverter<T> getConverter() {
            return null;
        }

        @Override
        public final @NotNull RedisListResult parse(@Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<?> converted = getConverter() == null ? encoded : getConverter().convert(encoded);
            return new RedisListResult(getType(), Wrapper.wrapList(converted));
        }
    }

    private static abstract class MapResultParser<T> implements ResultParser {
        protected abstract @NotNull String getType();
        protected abstract @NotNull Builder<Map<String, T>> getBuilder();
        protected @Nullable SimpleConverter<T> getConverter() {
            return null;
        }

        @Override
        public final @NotNull RedisMapResult parse(@Nullable Object data) {
            Map<String, T> encoded = getBuilder().build(data);
            Map<String, ?> converted = getConverter() == null ? encoded : getConverter().convert(encoded);
            return new RedisMapResult(getType(), Wrapper.wrapMap(converted));
        }
    }

    private static abstract class ObjectResultParser<T> implements ResultParser {
        protected abstract @NotNull Map<String, String> getType();
        protected abstract @NotNull Builder<List<T>> getBuilder();
        protected abstract @NotNull ObjectConverter<T> getConverter();

        @Override
        public final @NotNull RedisObjectResult parse(@Nullable Object data) {
            List<T> encoded = getBuilder().build(data);
            List<Map<String, Object>> converted = getConverter().convert(encoded);
            return new RedisObjectResult(getType(), Wrapper.wrapObject(converted));
        }
    }
}
