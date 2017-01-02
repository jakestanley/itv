package uk.co.jakestanley.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 686600714451922262L;

	public NotFoundException(String message) {
		super(message);
	}

}
