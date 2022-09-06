package jdbc.client.helpers.result.parser.builder;

import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.util.SafeEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static final Builder<List<Tuple>> TUPLE_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<Tuple> getBuilder() {
            return BuilderFactory.TUPLE;
        }

        // TODO: getListBuilder()
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

    public static final Builder<List<AccessControlUser>> ACCESS_CONTROL_USER_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<AccessControlUser> getBuilder() {
            return BuilderFactory.ACCESS_CONTROL_USER;
        }
    };

    public static final Builder<List<StreamEntryID>> STREAM_ENTRY_ID_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<StreamEntryID> getBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID;
        }

        @Override
        protected Builder<List<StreamEntryID>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_ID_LIST;
        }
    };

    public static final Builder<List<StreamEntry>> STREAM_ENTRY_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<StreamEntry> getBuilder() {
            return BuilderFactory.STREAM_ENTRY;
        }

        @Override
        protected Builder<List<StreamEntry>> getListBuilder() {
            return BuilderFactory.STREAM_ENTRY_LIST;
        }
    };

    public static final Builder<List<StreamInfo>> STREAM_INFO = new ListBuilder<>() {
        @Override
        protected Builder<StreamInfo> getBuilder() {
            return BuilderFactory.STREAM_INFO;
        }
    };

    public static final Builder<List<ScanResult<String>>> STRING_SCAN_RESULT_RESULT = new ListBuilder<>() {
        @Override
        protected Builder<ScanResult<String>> getBuilder() {
            return BuilderFactoryEx.STRING_SCAN_RESULT;
        }
    };

    public static final Builder<ScanResult<String>> STRING_SCAN_RESULT = new Builder<>() {
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


    private static abstract class ListBuilder<T> extends Builder<List<T>> {

        protected abstract Builder<T> getBuilder();

        protected Builder<List<T>> getListBuilder() {
            return null;
        }

        @Override
        public List<T> build(Object data) {
            Builder<List<T>> listBuilder = getListBuilder();
            if (listBuilder != null && data instanceof List) return listBuilder.build(data);
            return Collections.singletonList(getBuilder().build(data));
        }

        @Override
        public String toString() {
            return String.format("List<%s>", getBuilder().toString());
        }
    }
}
