package jdbc.client.structures.result;

import java.util.List;
import java.util.Map;

public class RedisObjectResult implements RedisResult {

    private final List<Map<String, Object>> result;
    private final Map<String, String> type;

    public RedisObjectResult(List<Map<String, Object>> result, Map<String, String> type) {
        this.result = result;
        this.type = type;
    }

    @Override
    public List<Map<String, Object>> getResult() {
        return result;
    }

    @Override
    public Map<String, String> getType() {
        return type;
    }
}

