package util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Every {@link container.Container} is equipped with a metadata storage. This is useful for
 * storing information required to re-open the {@link container.Container} (e.g., the block size)
 * or for re-creating index structures (e.g., the key of root node of a tree). 
 */
public class MetaData {

	/** The backing properties */
	private final Properties properties;

	/**
	 * Constructs a new MetaData instance
	 */
	public MetaData() {
		this.properties = new Properties();
	}

	/**
	 * Stores all meta data in the given location.
	 * @param file The location of the metadata file.
	 * @throws IOException On any error while writing the file.
	 */
	public void writeTo(Path file) throws IOException {
		try (Writer w = Files.newBufferedWriter(file, StandardOpenOption.WRITE, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
			properties.store(w, null);
		}
	}

	/**
	 * Loads all meta data stored in the given location. 
	 * @param path The location of the metadata file.
	 * @throws IOException On any error while reading the file.
	 */
	public void readFrom(Path path) throws IOException {
		try (Reader r = Files.newBufferedReader(path)) {
			properties.load(r);
		}
	}
	
	/**
	 * Set the given property to the given value
	 * 
	 * @param name  The name of the property to set.
	 * @param value The value to set.
	 */
	public void setProperty(String name, String value) {
		properties.setProperty(name, value);
	}

	/**
	 * Returns the property for the given name. If no property is set, an
	 * {@link NoSuchElementException} is thrown.
	 * 
	 * @param name The name of the property
	 * @return The value of the given property name.
	 * @throws NoSuchElementException If the property is not set.
	 */
	public String getProperty(String name) {
		String result = properties.getProperty(name);
		if (result == null)
			throw new NoSuchElementException("Property \"" + name + "\" not set.");
		else
			return result;
	}

	/**
	 * Returns the property for the given name. If no property is set, the given
	 * default value is returned.
	 * 
	 * @param name         The name of the property
	 * @param defaultValue The default value
	 * @return The value of the given property name or the default value, if the
	 *         property is not set.
	 */
	public String getProperty(String name, String defaultValue) {
		return properties.getProperty(name, defaultValue);
	}

	/**
	 * Set the given property to the given value
	 * 
	 * @param name  The name of the property to set.
	 * @param value The value to set.
	 */
	public void setIntProperty(String name, int value) {
		setProperty(name, Integer.toString(value));
	}

	/**
	 * Returns the property for the given name. If no property is set, an
	 * {@link NoSuchElementException} is thrown.
	 * 
	 * @param name The name of the property
	 * @return The value of the given property name.
	 * @throws NoSuchElementException If the property is not set.
	 */
	public int getIntProperty(String name) {
		return Integer.parseInt(getProperty(name));
	}

	/**
	 * Returns the property for the given name. If no property is set, the given
	 * default value is returned.
	 * 
	 * @param name         The name of the property
	 * @param defaultValue The default value
	 * @return The value of the given property name or the default value, if the
	 *         property is not set.
	 */
	public int getIntProperty(String name, int defaultValue) {
		return Integer.parseInt(getProperty(name, Integer.toString(defaultValue)));
	}

	/**
	 * Set the given property to the given value
	 *
	 * @param name  The name of the property to set.
	 * @param value The value to set.
	 */
	public void setLongProperty(String name, long value) {
		setProperty(name, Long.toString(value));
	}

	/**
	 * Returns the property for the given name. If no property is set, an
	 * {@link NoSuchElementException} is thrown.
	 *
	 * @param name The name of the property
	 * @return The value of the given property name.
	 * @throws NoSuchElementException If the property is not set.
	 */
	public long getLongProperty(String name) {
		return Long.parseLong(getProperty(name));
	}

	/**
	 * Returns the property for the given name. If no property is set, the given
	 * default value is returned.
	 *
	 * @param name         The name of the property
	 * @param defaultValue The default value
	 * @return The value of the given property name or the default value, if the
	 *         property is not set.
	 */
	public long getLongProperty(String name, long defaultValue) {
		return Long.parseLong(getProperty(name, Long.toString(defaultValue)));
	}

	/**
	 * Set the given property to the given value
	 *
	 * @param name  The name of the property to set.
	 * @param value The value to set.
	 */
	public void setFloatProperty(String name, float value) {
		setProperty(name, Float.toString(value));
	}

	/**
	 * Returns the property for the given name. If no property is set, an
	 * {@link NoSuchElementException} is thrown.
	 *
	 * @param name The name of the property
	 * @return The value of the given property name.
	 * @throws NoSuchElementException If the property is not set.
	 */
	public float getFloatProperty(String name) {
		return Float.parseFloat(getProperty(name));
	}

	/**
	 * Returns the property for the given name. If no property is set, the given
	 * default value is returned.
	 * 
	 * @param name         The name of the property
	 * @param defaultValue The default value
	 * @return The value of the given property name or the default value, if the
	 *         property is not set.
	 */
	public float getFloatProperty(String name, float defaultValue) {
		return Float.parseFloat(getProperty(name, Float.toString(defaultValue)));
	}

	/**
	 * Set the given property to the given value
	 * 
	 * @param name  The name of the property to set.
	 * @param value The value to set.
	 */
	public void setDoubleProperty(String name, double value) {
		setProperty(name, Double.toString(value));
	}

	/**
	 * Returns the property for the given name. If no property is set, an
	 * {@link NoSuchElementException} is thrown.
	 * 
	 * @param name The name of the property
	 * @return The value of the given property name.
	 * @throws NoSuchElementException If the property is not set.
	 */
	public double getDoubleProperty(String name) {
		return Double.parseDouble(getProperty(name));
	}

	/**
	 * Returns the property for the given name. If no property is set, the given
	 * default value is returned.
	 * 
	 * @param name         The name of the property
	 * @param defaultValue The default value
	 * @return The value of the given property name or the default value, if the
	 *         property is not set.
	 */
	public double getFloatProperty(String name, double defaultValue) {
		return Double.parseDouble(getProperty(name, Double.toString(defaultValue)));
	}

}
