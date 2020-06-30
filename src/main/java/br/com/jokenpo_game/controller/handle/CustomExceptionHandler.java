package br.com.jokenpo_game.controller.handle;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.jokenpo_game.domain.exceptions.ApiResponseError;
import br.com.jokenpo_game.domain.exceptions.NegocioException;
import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public final ResponseEntity<ApiResponseError> recursoNaoEncontradoHandler(RecursoNaoEncontradoException ex, WebRequest request) {
		ApiResponseError apiResponseError = new ApiResponseError(ex.getMessage(), new Date());
		return new ResponseEntity<ApiResponseError>(apiResponseError, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiResponseError apiResponseError = new ApiResponseError("O recurso solicitado esta indisponivel ou foi movido", new Date());
		return new ResponseEntity<Object>(apiResponseError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NegocioException.class)
	public final ResponseEntity<ApiResponseError> erroDeNegocioException(NegocioException ex, WebRequest request) {
		ApiResponseError apiResponseError = new ApiResponseError(ex.getMessage(), new Date());
		return new ResponseEntity<ApiResponseError>(apiResponseError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiResponseError> ErroInterno(Exception ex, WebRequest request) {
		ApiResponseError apiResponse = new ApiResponseError("Ocorreu um erro ao processar a requisição", new Date());
		return new ResponseEntity<ApiResponseError>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
