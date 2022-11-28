package jdbc.client.helpers.result.parser.converter;

import jdbc.client.helpers.result.parser.converter.type.TypeFactory;
import jdbc.client.structures.result.ObjectType;
import jdbc.client.structures.result.SimpleType;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Module;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.util.KeyValue;

import java.util.List;
import java.util.Map;

public class ConverterFactory {

    private ConverterFactory() {
    }

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

    public abstract static class IdentityConverter<T> extends SimpleConverter<T, T> {
        @Override
        protected @NotNull T convertImpl(@NotNull T encoded) {
            return encoded;
        }

        @Override
        public @NotNull List<T> convertListImpl(@NotNull List<T> encoded) {
            return encoded;
        }

        @Override
        public @NotNull Map<String, T> convertMapImpl(@NotNull Map<String, T> encoded) {
            return encoded;
        }
    }


    public static final ObjectConverter<Tuple> TUPLE = new ObjectConverter<>() {
        @Override
        public ObjectType<Tuple> getObjectType() {
            return TypeFactory.TUPLE;
        }
    };

    public static final ObjectConverter<KeyedZSetElement> KEYED_TUPLE = new ObjectConverter<>() {
        @Override
        public ObjectType<KeyedZSetElement> getObjectType() {
            return TypeFactory.KEYED_ZSET_ELEMENT;
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
        protected @NotNull String convertImpl(@NotNull HostAndPort encoded) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<Slowlog> SLOW_LOG = new ObjectConverter<>() {
        @Override
        public ObjectType<Slowlog> getObjectType() {
            return TypeFactory.SLOW_LOG;
        }
    };

    public static final SimpleConverter<StreamEntryID, String> STREAM_ENTRY_ID = new SimpleConverter<>() {
        @Override
        public SimpleType<String> getSimpleType() {
            return TypeFactory.STRING;
        }

        @Override
        public @NotNull String convertImpl(@NotNull StreamEntryID encoded) {
            return encoded.toString();
        }
    };

    public static final ObjectConverter<StreamEntry> STREAM_ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<StreamEntry> getObjectType() {
            return TypeFactory.STREAM_ENTRY;
        }
    };

    public static final ObjectConverter<Map.Entry<String, List<StreamEntry>>> STREAM_READ_ENTRY = new ObjectConverter<>() {
        @Override
        public ObjectType<Map.Entry<String, List<StreamEntry>>> getObjectType() {
            return TypeFactory.STREAM_READ_ENTRY;
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
}
