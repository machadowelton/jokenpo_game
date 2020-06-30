package br.com.jokenpo_game.domain.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ApiResponseError implements Serializable {

	private static final long serialVersionUID = 4396916002323482790L;
	
	private String mensagem;
	
	private Date data;

	public ApiResponseError() {
		super();
	}

	public ApiResponseError(String mensagem, Date data) {
		super();
		this.mensagem = mensagem;
		this.data = data;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}		
	
}
