package io;

/**
 * A FixedSizeSerializer guarantees that all instances of the serialized type are 
 * represented by the same amount of bytes in their serialized form.
 * @param <T> The type to (de-)serialize
 */
public interface FixedSizeSerializer<T> extends Serializer<T> {
	
	/**
	 * Calculates the number of bytes required for the serialized form of the given object
	 * @return the number of bytes required for the serialized form of the given object
	 */
	int getSerializedSize();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	default int getSerializedSize(T value) {
		return getSerializedSize();
	}

}
