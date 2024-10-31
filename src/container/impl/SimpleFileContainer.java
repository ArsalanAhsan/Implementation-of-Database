package container.impl;

import container.Container;
import io.FixedSizeSerializer;
import util.MetaData;

import java.nio.file.Path;
import java.util.NoSuchElementException;

public class SimpleFileContainer<Value> implements Container<Long, Value> {

	public SimpleFileContainer(Path directory, String filenamePrefix, FixedSizeSerializer<Value> serializer) {
		// TODO
	}
	
	@Override
	public MetaData getMetaData() {
		// TODO
		return null;
	}

	@Override
	public void open() {
		// TODO
	}

	@Override
	public void close() {
		// TODO
	}
	
	@Override
	public Long reserve() throws IllegalStateException {
		// TODO
		return null;
	}
	
	@Override
	public void update(Long key, Value value) throws NoSuchElementException {
		// TODO
	}


	@Override
	public Value get(Long key) throws NoSuchElementException {
		// TODO
		return null;
	}

	@Override
	public void remove(Long key) throws NoSuchElementException {
		// TODO
	}
}
