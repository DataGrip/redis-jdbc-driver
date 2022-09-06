package jdbc.client.structures.result;

import java.util.Map;

public class RedisMapResult implements RedisResult {

    private final Map<String, Object> result;
    private final String type;

    public RedisMapResult(Map<String, Object> result, String type) {
        this.result = result;
        this.type = type;
    }

    @Override
    public Map<String, Object> getResult() {
        return result;
    }

    @Override
    public String getType() {
        return type;
    }
}
