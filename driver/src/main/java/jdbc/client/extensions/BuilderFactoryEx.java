package jdbc.client.extensions;

import redis.clients.jedis.Builder;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.util.SafeEncoder;

import java.util.ArrayList;
import java.util.List;

/* redis.clients.jedis.BuilderFactory */
public class BuilderFactoryEx {

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


}
