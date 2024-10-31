package container.impl;

import container.Container;
import util.MetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapContainer<Value> implements Container<Long, Value> {
	private Map<Long, Value> map;
	private long nextKey;
	private MetaData metaData;

	public MapContainer() {
		this.map = new HashMap<>();
		this.nextKey = 0;
		this.metaData = new MetaData();
	}

	@Override
	public MetaData getMetaData() {
		// Updating metadata to reflect current state of the container.
		metaData.setLongProperty("totalEntries", map.size());
		metaData.setLongProperty("nextKey", nextKey);
		return metaData;
	}

	@Override
	public void open() {
		// Initialize or reset the map when "opening" the container.
		this.map = new HashMap<>();
		this.nextKey = 0;
		metaData.setLongProperty("totalEntries", 0);
		metaData.setLongProperty("nextKey", nextKey);
	}

	@Override
	public void close() {
		// Clear the map and reset metadata when "closing" the container.
		map.clear();
		metaData.setLongProperty("totalEntries", 0);
		metaData.setLongProperty("nextKey", 0);
	}

	@Override
	public Long reserve() throws IllegalStateException {
		// Generates a new key and reserves it without adding a value yet.
		long reservedKey = nextKey++;
		metaData.setLongProperty("nextKey", nextKey); // Update next key in metadata.
		return reservedKey;
	}

	@Override
	public Value get(Long key) throws NoSuchElementException {
		if (!map.containsKey(key)) {
			throw new NoSuchElementException("Key not found: " + key);
		}
		return map.get(key);
	}

	@Override
	public void update(Long key, Value value) throws NoSuchElementException {
		if (!map.containsKey(key)) {
			throw new NoSuchElementException("Key not found: " + key);
		}
		map.put(key, value);
	}

	@Override
	public void remove(Long key) throws NoSuchElementException {
		if (!map.containsKey(key)) {
			throw new NoSuchElementException("Key not found: " + key);
		}
		map.remove(key);
		metaData.setLongProperty("totalEntries", map.size()); // Update entry count in metadata.
	}
}