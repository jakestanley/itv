package uk.co.jakestanley.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadOfferException extends Exception {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6586557486633805142L;

	public BadOfferException(String message){
		super(message);
	}
	
}
