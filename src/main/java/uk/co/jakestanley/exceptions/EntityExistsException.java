package uk.co.jakestanley.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityExistsException extends Exception {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -897089965935016385L;

	public EntityExistsException(String message){
		super(message);
	}
	
}
