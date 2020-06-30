package br.com.jokenpo_game.domain.enums;

public enum EStatusPartida {
	
	ABERTA("ABERTA"),
	FECHADA("FECHADA");
	
	private final String value;
	
	EStatusPartida(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}		
	
}
