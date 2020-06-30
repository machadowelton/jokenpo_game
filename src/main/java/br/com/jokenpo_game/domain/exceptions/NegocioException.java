package br.com.jokenpo_game.domain.exceptions;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -8257877576734613397L;

	public NegocioException(String message) {
		super(message);
		
	}
}
