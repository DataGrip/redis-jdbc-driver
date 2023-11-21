package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Params {

    private final String[] params;
    private Set<String> paramNamesSet;

    public Params(@NotNull String[] params) {
        this.params = params;
    }

    public @NotNull String[] getRawParams() {
        return params;
    }

    public @Nullable String getFirst() {
        return params.length > 0 ? params[0] : null;
    }

    public @Nullable String getNext(@NotNull Keyword keyword) {
        int nextIndex = params.length;
        String keywordName = Utils.getName(keyword);
        for (int i = 0; i < params.length; ++i) {
            if (Utils.getName(params[i]).equals(keywordName)) {
                nextIndex = i;
            }
        }
        return nextIndex < params.length ? params[nextIndex] : null;
    }

    public int getLength() {
        return params.length;
    }

    public boolean contains(@NotNull Keyword keyword) {
        if (paramNamesSet == null) {
            paramNamesSet = Arrays.stream(params).map(Utils::getName).collect(Collectors.toSet());
        }
        return paramNamesSet.contains(keyword.name());
    }
}
