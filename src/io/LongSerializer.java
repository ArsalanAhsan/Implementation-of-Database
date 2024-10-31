package io;

import java.nio.ByteBuffer;

public class LongSerializer implements FixedSizeSerializer<Long> {

    @Override
    public void serialize(Long value, ByteBuffer buffer) {
        buffer.putLong(value);
    }

    @Override
    public Long deserialize(ByteBuffer buffer) {
        return buffer.getLong();
    }

    @Override
    public int getSerializedSize() {
        return 8;
    }
}
