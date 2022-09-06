package jdbc.client.helpers.result.parser;

import jdbc.client.structures.result.RedisResult;

public interface ResultParser {
    RedisResult parse(Object data);
}
