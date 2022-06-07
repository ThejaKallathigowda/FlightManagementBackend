/**
 * 
 */
package com.flightApp.cts.exeption;

/**
 * @author Sanchit Singhal
 *
 */
public class NullFlightDetailsException extends RuntimeException {

	/**
	 * @param message
	 * @param cause
	 */
	public NullFlightDetailsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NullFlightDetailsException(String message) {
		super(message);
	}
	
}
