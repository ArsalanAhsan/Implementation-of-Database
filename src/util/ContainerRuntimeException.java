package util;

import java.io.Serial;

/**
 *	Exception used to signal unexpected errors during {@link container.Container} access.
 */
public class ContainerRuntimeException extends RuntimeException {

	/** The serialVersionUID */
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ContainerRuntimeException
	 * @param message A message describing the error
	 */
	public ContainerRuntimeException(String message) {
		super(message);
	}

	/**
	 * Creates a new ContainerRuntimeException
	 * @param message A message describing the error
	 * @param cause The cause of this exception
	 */
	public ContainerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
