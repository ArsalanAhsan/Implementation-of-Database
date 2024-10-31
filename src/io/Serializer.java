package io;

import java.nio.ByteBuffer;

/**
 * A serializer is used to convert an object into an equivalent byte 
 * representation and vice versa. Typically, serializers are required when
 * data is stored on disk or sent over the network. 
 * @param <T> The type to (de-)serialize
 */
public interface Serializer<T> {

	/**
	 * Calculates the number of bytes required for the serialized form of the given object
	 * @param value the object to calculate the serialized size of
	 * @return the number of bytes required for the serialized form of the given object
	 */
	int getSerializedSize( T value );
	
	/**
	 * Serializes the given value into the given byte buffer. The caller is in charge to ensure, that the buffer 
	 * provides enough space for serialization.
	 * @param value the object to serialize
	 * @param buffer the buffer to write to
	 */
	void serialize( T value, ByteBuffer buffer );
	
	/**
	 * Deserializes an instance of T and returns it
	 * @param buffer the buffer holding the serialized form
	 * @return the deserialized instance of T
	 */
	T deserialize( ByteBuffer buffer );

}
