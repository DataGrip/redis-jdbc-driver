package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RedisResult {
    @NotNull Object getType();
    @NotNull Object getResult();
    @Nullable ColumnHint getColumnHint();
}
