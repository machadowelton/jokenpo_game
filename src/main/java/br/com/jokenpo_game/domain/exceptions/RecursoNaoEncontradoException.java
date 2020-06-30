package br.com.jokenpo_game.domain.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 481319993295038761L;

	public RecursoNaoEncontradoException(String message) {
		super(message);
	}

}
