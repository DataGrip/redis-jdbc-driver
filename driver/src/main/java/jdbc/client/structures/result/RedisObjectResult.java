package jdbc.client.structures.result;

import java.util.List;
import java.util.Map;

public class RedisObjectResult implements RedisResult {

    private final Map<String, String> type;
    private final List<Map<String, Object>> result;

    public RedisObjectResult(Map<String, String> type, List<Map<String, Object>> result) {
        this.type = type;
        this.result = result;
    }

    @Override
    public Map<String, String> getType() {
        return type;
    }

    @Override
    public List<Map<String, Object>> getResult() {
        return result;
    }
}

