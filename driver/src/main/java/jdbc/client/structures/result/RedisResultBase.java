package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.Nullable;

abstract class RedisResultBase implements RedisResult {

    private final ColumnHint columnHint;

    protected RedisResultBase(@Nullable ColumnHint columnHint) {
        this.columnHint = columnHint;
    }

    @Override
    public @Nullable ColumnHint getColumnHint() {
        return columnHint;
    }
}
