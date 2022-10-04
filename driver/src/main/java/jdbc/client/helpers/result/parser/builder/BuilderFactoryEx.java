package jdbc.client.helpers.result.parser.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Module;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.util.SafeEncoder;

import java.util.*;

/* redis.clients.jedis.BuilderFactory */
public class BuilderFactoryEx {

    private BuilderFactoryEx() {
    }

    public static final Builder<List<Object>> RESULT = new Builder<>() {
        @Override
        @SuppressWarnings("unchecked")
        public List<Object> build(Object data) {
            Object encoded = BuilderFactory.ENCODED_OBJECT.build(data);
            if (encoded instanceof List) return (List<Object>) encoded;
            return Collections.singletonList(encoded);
        }
    };

    public static final Builder<List<Long>> LONG_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<Long> getBuilder() {
            return BuilderFactory.LONG;
        }

        @Override
        protected Builder<List<Long>> getListBuilder() {
            return BuilderFactory.LONG_LIST;
        }
    };

    public static final Builder<List<Double>> DOUBLE_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<Double> getBuilder() {
            return BuilderFactory.DOUBLE;
        }

        @Override
        protected Builder<List<Double>> getListBuilder() {
            return BuilderFactory.DOUBLE_LIST;
        }
    };

    public static final Builder<List<Boolean>> BOOLEAN_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<Boolean> getBuilder() {
            return BuilderFactory.BOOLEAN;
        }

        @Override
        protected Builder<List<Boolean>> getListBuilder() {
            return BuilderFactory.BOOLEAN_LIST;
        }
    };

    public static final Builder<List<byte[]>> BYTE_ARRAY_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<byte[]> getBuilder() {
            return BuilderFactory.BYTE_ARRAY;
        }

        @Override
        protected Builder<List<byte[]>> getListBuilder() {
            return BuilderFactory.BYTE_ARRAY_LIST;
        }
    };

    // TODO: use Iterable instead of conversation list to set
    public static final Builder<List<Tuple>> TUPLE_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<Tuple> getBuilder() {
            return BuilderFactory.TUPLE;
        }

        private final Builder<List<Tuple>> TUPLE_LIST = new Builder<>() {
            @Override
            public List<Tuple> build(Object data) {
                Set<Tuple> tupleSet = BuilderFactory.TUPLE_ZSET.build(data);
                return tupleSet == null ? null : new ArrayList<>(tupleSet);
            }

            @Override
            public String toString() {
                return "List<Tuple>";
            }
        };

        @Override
        protected Builder<List<Tuple>> getListBuilder() {
            return TUPLE_LIST;
        }
    };

    public static final Builder<List<KeyedListElement>> KEYED_LIST_ELEMENT_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<KeyedListElement> getBuilder() {
            return BuilderFactory.KEYED_LIST_ELEMENT;
        }
    };

    public static final Builder<List<KeyedZSetElement>> KEYED_ZSET_ELEMENT_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<KeyedZSetElement> getBuilder() {
            return BuilderFactory.KEYED_ZSET_ELEMENT;
        }
    };

    public static final Builder<List<GeoCoordinate>> GEO_COORDINATE = new ListBuilder<>() {
        @Override
        protected Builder<List<GeoCoordinate>> getListBuilder() {
            return BuilderFactory.GEO_COORDINATE_LIST;
        }
    };

    public static final Builder<List<GeoRadiusResponse>> GEORADIUS_RESPONSE = new ListBuilder<>() {
        @Override
        protected Builder<List<GeoRadiusResponse>> getListBuilder() {
            return BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT;
        }
    };

    public static final Builder<List<Module>> MODULE = new ListBuilder<>() {
        @Override
        protected Builder<List<Module>> getListBuilder() {
            return BuilderFactory.MODULE_LIST;
        }
    };

    public static final Builder<List<AccessControlUser>> ACCESS_CONTROL_USER = new ListBuilder<>() {
        @Override
        protected Builder<AccessControlUser> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final Builder<List<AccessControlLogEntry>> ACCESS_CONTROL_LOG_ENTRY = new ListBuilder<>() {
        @Override
        protected Builder<List<AccessControlLogEntry>> getListBuilder() {
            return BuilderFactory.ACCESS_CONTROL_LOG_ENTRY_LIST;
        }
    };

    public static final Builder<List<StreamEntryID>> STREAM_ENTRY_ID = new ListBuilder<>() {
        @Override
        protected Builder<StreamEntryID> getBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected Builder<List<StreamEntryID>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final Builder<List<StreamEntry>> STREAM_ENTRY = new ListBuilder<>() {
        @Override
        protected Builder<StreamEntry> getBuilder() {
            return BuilderFactory.STREAM_ENTRY;
        }

        @Override
        protected Builder<List<StreamEntry>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final Builder<List<Map.Entry<String, List<StreamEntry>>>> STREAM_READ = new ListBuilder<>() {
        @Override
        protected Builder<List<Map.Entry<String, List<StreamEntry>>>> getListBuilder() {
            return BuilderFactory.STREAM_READ_RESPONSE;
        }
    };

    public static final Builder<List<StreamInfo>> STREAM_INFO = new ListBuilder<>() {
        @Override
        protected Builder<StreamInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final Builder<List<StreamGroupInfo>> STREAM_GROUP_INFO = new ListBuilder<>() {
        @Override
        protected Builder<List<StreamGroupInfo>> getListBuilder() {
            return BuilderFactory.STREAM_GROUP_INFO_LIST;
        }
    };

    public static final Builder<List<StreamConsumersInfo>> STREAM_CONSUMERS_INFO = new ListBuilder<>() {
        @Override
        protected Builder<List<StreamConsumersInfo>> getListBuilder() {
            return BuilderFactory.STREAM_CONSUMERS_INFO_LIST;
        }
    };

    public static final Builder<List<ScanResult<String>>> STRING_SCAN_RESULT = new ListBuilder<>() {

        private final Builder<ScanResult<String>> STRING_SCAN_RESULT = new Builder<>() {
            @Override
            @SuppressWarnings("unchecked")
            public ScanResult<String> build(Object data) {
                if (null == data) {
                    return null;
                }
                List<?> l = (List<?>) data;
                String cursor = SafeEncoder.encode((byte[]) l.get(0));
                List<byte[]> rl = (List<byte[]>) l.get(1);
                final ArrayList<String> results = new ArrayList<>(l.size());
                for (final byte[] barray : rl) {
                    if (barray == null) {
                        results.add(null);
                    } else {
                        results.add(SafeEncoder.encode(barray));
                    }
                }
                return new ScanResult<>(cursor, results);
            }

            @Override
            public String toString() {
                return "ScanResult<String>";
            }
        };

        @Override
        protected Builder<ScanResult<String>> getBuilder() {
            return STRING_SCAN_RESULT;
        }
    };

    private static abstract class ListBuilder<T> extends Builder<List<T>> {

        protected @Nullable Builder<T> getBuilder() {
            return null;
        }

        protected @Nullable Builder<List<T>> getListBuilder() {
            return null;
        }

        @Override
        public @NotNull List<T> build(@Nullable Object data) {
            Builder<List<T>> listBuilder = getListBuilder();
            Builder<T> builder = getBuilder();
            if (data != null) {
                if (listBuilder != null && data instanceof List) return listBuilder.build(data);
                if (builder != null) return Collections.singletonList(builder.build(data));
            }
            return builder == null ? Collections.emptyList() : Collections.singletonList(null);
        }

        @Override
        public String toString() {
            return String.format("List<%s>", getBuilder());
        }
    }
}
