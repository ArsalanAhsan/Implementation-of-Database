package container.impl;


import container.Container;
import io.FixedSizeSerializer;
import util.MetaData;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.NoSuchElementException;

public class SimpleFileContainer<Value> implements Container<Long, Value> {

	private Path dataFile;
	private Path metaDataFile;
	private FixedSizeSerializer<Value> serializer;
	private MetaData metaData;
	private long nextKey;

	public SimpleFileContainer(Path directory, String filenamePrefix, FixedSizeSerializer<Value> serializer) {
		this.dataFile = directory.resolve(filenamePrefix + "_data.dat");
		this.metaDataFile = directory.resolve(filenamePrefix + "_metadata.properties");
		this.serializer = serializer;
		this.metaData = new MetaData();
	}

	@Override
	public MetaData getMetaData() {
		return metaData;
	}

	@Override
	public void open() {
		try {
			// Load metadata from file
			if (Files.exists(metaDataFile)) {
				metaData.readFrom(metaDataFile);
				nextKey = metaData.getLongProperty("nextKey", 0);
			} else {
				nextKey = 0;
				metaData.setLongProperty("nextKey", nextKey);
				metaData.setLongProperty("totalEntries", 0);
			}

			// Create data file if it doesn't exist
			if (!Files.exists(dataFile)) {
				Files.createFile(dataFile);
			}

		} catch (IOException e) {
			throw new RuntimeException("Error opening container", e);
		}
	}

	@Override
	public void close() {
		try {
			// Save metadata to file
			metaData.setLongProperty("nextKey", nextKey);
			metaData.writeTo(metaDataFile);
		} catch (IOException e) {
			throw new RuntimeException("Error closing container", e);
		}
	}

	@Override
	public Long reserve() throws IllegalStateException {
		long reservedKey = nextKey++;
		metaData.setLongProperty("nextKey", nextKey);
		return reservedKey;
	}

	@Override
	public void update(Long key, Value value) throws NoSuchElementException {
		try (RandomAccessFile file = new RandomAccessFile(dataFile.toFile(), "rw")) {
			long position = key * serializer.getSerializedSize();
			file.seek(position);
			ByteBuffer buffer = ByteBuffer.allocate(serializer.getSerializedSize());
			serializer.serialize(value, buffer);
			buffer.flip(); // Prepare the buffer for writing
			file.write(buffer.array());
		} catch (IOException e) {
			throw new RuntimeException("Error updating value", e);
		}
	}

	@Override
	public Value get(Long key) throws NoSuchElementException {
		try (RandomAccessFile file = new RandomAccessFile(dataFile.toFile(), "r")) {
			long position = key * serializer.getSerializedSize();
			file.seek(position);

			byte[] data = new byte[serializer.getSerializedSize()];
			int bytesRead = file.read(data);
			if (bytesRead != data.length) {
				throw new NoSuchElementException("Key not found: " + key);
			}
			ByteBuffer buffer = ByteBuffer.wrap(data);
			return serializer.deserialize(buffer);
		} catch (IOException e) {
			throw new RuntimeException("Error retrieving value", e);
		}
	}

	@Override
	public void remove(Long key) throws NoSuchElementException {
		try (RandomAccessFile file = new RandomAccessFile(dataFile.toFile(), "rw")) {
			long position = key * serializer.getSerializedSize();
			file.seek(position);

			byte[] data = new byte[serializer.getSerializedSize()];
			for (int i = 0; i < data.length; i++) {
				data[i] = 0; // Mark as deleted by zeroing out the bytes
			}
			file.write(data);

			// Update metadata
			long totalEntries = metaData.getLongProperty("totalEntries", 0) - 1;
			metaData.setLongProperty("totalEntries", totalEntries);

		} catch (IOException e) {
			throw new RuntimeException("Error removing value", e);
		}
	}
}
