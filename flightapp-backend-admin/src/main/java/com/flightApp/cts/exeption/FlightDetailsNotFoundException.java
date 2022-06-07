/**
 * 
 */
package com.flightApp.cts.exeption;

/**
 * @author Sanchit Singhal
 *
 */
public class FlightDetailsNotFoundException extends RuntimeException {
	/**
	 * @param message
	 * @param cause
	 */
	public FlightDetailsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlightDetailsNotFoundException(String s) {
		super(s);
	}

}
