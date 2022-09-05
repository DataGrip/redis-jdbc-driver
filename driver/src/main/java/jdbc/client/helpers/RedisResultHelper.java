package jdbc.client.helpers;

import java.util.List;
import java.util.Map;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    public static List<Map<String, Object>> parseResult(RedisQuery query, Object rawResult) {
        Object encodedResult = RedisResultEncoder.encodeResult(query, rawResult);
        return RedisResultConverter.convertResult(encodedResult);
    }
}
