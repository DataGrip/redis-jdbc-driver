package jdbc.client.helpers;

import redis.clients.jedis.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.util.Slowlog;

import java.lang.Module;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisResultConverter {

    private RedisResultConverter() {
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> convertResult(Object encodedResult) {
        Object result = convert(encodedResult);
        if (result instanceof List) {
            return  ((List<?>) result).stream().map(o -> Map.of("value", (Object) o)).collect(Collectors.toList());
        }
        if (result instanceof LinkedHashMap) {
            return Collections.singletonList((LinkedHashMap<String, Object>) result);
        }
        if (result instanceof Map) {
            return ((Map<?, ?>) result).entrySet().stream().map(o -> new LinkedHashMap<String, Object>() {{
                put("field", o.getKey());
                put("value", o.getValue());
            }}).collect(Collectors.toList());
        }
        return Collections.singletonList(Map.of("value", encodedResult));
    }

    private static Object convert(Object encodedResult) {
        if (encodedResult instanceof String) return convert((String) encodedResult);
        if (encodedResult instanceof Long) return convert((Long) encodedResult);
        if (encodedResult instanceof Boolean) return convert((Boolean) encodedResult);
        if (encodedResult instanceof byte[]) return convert((byte[]) encodedResult);
        if (encodedResult instanceof AccessControlUser) return convert((AccessControlUser) encodedResult);
        if (encodedResult instanceof AccessControlLogEntry) return convert((AccessControlLogEntry) encodedResult);
        if (encodedResult instanceof KeyedListElement) return convert((KeyedListElement) encodedResult);
        if (encodedResult instanceof KeyedZSetElement) return convert((KeyedZSetElement) encodedResult);
        if (encodedResult instanceof Tuple) return convert((Tuple) encodedResult);
        if (encodedResult instanceof GeoCoordinate) return convert((GeoCoordinate) encodedResult);
        if (encodedResult instanceof GeoRadiusResponse) return convert((GeoRadiusResponse) encodedResult);
        if (encodedResult instanceof Slowlog) return convert((Slowlog) encodedResult);
        if (encodedResult instanceof Module) return convert((Module) encodedResult);
        if (encodedResult instanceof StreamEntryID) return convert((StreamEntryID) encodedResult);
        if (encodedResult instanceof StreamEntry) return convert((StreamEntry) encodedResult);
        if (encodedResult instanceof StreamPendingEntry) return convert((StreamPendingEntry) encodedResult);
        if (encodedResult instanceof StreamPendingSummary) return convert((StreamPendingSummary) encodedResult);
        if (encodedResult instanceof StreamInfo) return convert((StreamInfo) encodedResult);
        if (encodedResult instanceof StreamGroupInfo) return convert((StreamGroupInfo) encodedResult);
        if (encodedResult instanceof StreamConsumersInfo) return convert((StreamConsumersInfo) encodedResult);
        if (encodedResult instanceof ScanResult) return convert((ScanResult<?>) encodedResult);
        if (encodedResult instanceof Map.Entry) return convert((Map.Entry<?, ?>) encodedResult);
        if (encodedResult instanceof List) return convert((List<?>) encodedResult);
        if (encodedResult instanceof Map) return convert((Map<?, ?>) encodedResult);
        return encodedResult;
    }

    private static Object convert(String result) {
        return result;
    }

    private static Object convert(Long result) {
        return result;
    }

    private static Object convert(Boolean result) {
        return result;
    }

    private static Object convert(byte[] result) {
        return result;
    }

    private static Object convert(AccessControlUser accessControlUser) {
        // TODO:
        return null;
    }

    private static Object convert(AccessControlLogEntry accessControlLogEntry) {
        // TODO:
        return null;
    }

    private static Object convert(KeyedListElement keyedListElement) {
        // TODO:
        return null;
    }

    private static Object convert(KeyedZSetElement keyedZSetElement) {
        // TODO:
        return null;
    }

    private static Object convert(Tuple tuple) {
        // TODO:
        return null;
    }

    private static Object convert(GeoCoordinate geoCoordinate) {
        // TODO:
        return null;
    }

    private static Object convert(GeoRadiusResponse geoRadiusResponse) {
        // TODO:
        return null;
    }

    private static Object convert(Slowlog slowlog) {
        // TODO:
        return null;
    }

    private static Object convert(Module module) {
        // TODO:
        return null;
    }

    private static Object convert(StreamEntryID streamEntryID) {
        return streamEntryID.toString();
    }

    private static Object convert(StreamEntry streamEntry) {
        return new LinkedHashMap<>() {{
            put("id", convert(streamEntry.getID()));
            put("value", convert(streamEntry.getFields()));
        }};
    }

    private static Object convert(StreamPendingEntry streamPendingEntry) {
        // TODO:
        return null;
    }

    private static Object convert(StreamPendingSummary streamPendingSummary) {
        // TODO:
        return null;
    }

    private static Object convert(StreamInfo streamInfo) {
        // TODO:
        return null;
    }

    private static Object convert(StreamGroupInfo streamGroupInfo) {
        // TODO:
        return null;
    }

    private static Object convert(StreamConsumersInfo streamConsumersInfo) {
        // TODO:
        return null;
    }

    private static Object convert(ScanResult<?> scanResult) {
        return new LinkedHashMap<>() {{
            put("cursor", convert(scanResult.getCursor()));
            put("results", convert(scanResult.getResult()));
        }};
    }

    private static Object convert(Map.Entry<?, ?> entry) {
        return new LinkedHashMap<>() {{
            put("field", convert(entry.getKey()));
            put("value", convert(entry.getValue()));
        }};
    }

    private static Object convert(Map<?, ?> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convert(e.getValue())));
    }

    private static Object convert(List<?> list) {
        return list.stream().map(RedisResultConverter::convert).collect(Collectors.toList());
    }
}
