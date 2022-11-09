package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.Nullable;

public interface RedisResult {
    @Nullable ColumnHint getColumnHint();
}
