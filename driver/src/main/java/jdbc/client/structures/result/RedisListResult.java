package jdbc.client.structures.result;

import java.util.List;

public class RedisListResult implements RedisResult {

    private final String type;
    private final List<Object> result;

    public RedisListResult(String type, List<Object> result) {
        this.type = type;
        this.result = result;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Object> getResult() {
        return result;
    }
}