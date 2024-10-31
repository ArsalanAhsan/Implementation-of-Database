package io;

import java.nio.ByteBuffer;

/**
 * Simple serializer von Integer values
 */
public class IntSerializer implements FixedSizeSerializer<Integer> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(Integer value, ByteBuffer buffer) {
		buffer.putInt(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deserialize(ByteBuffer buffer) {
		return buffer.getInt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSerializedSize() {
		return 4;
	}

}
