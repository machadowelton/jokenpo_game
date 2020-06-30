package br.com.jokenpo_game.domain.enums;

public enum EMovimento {
	
	TESOURA("TESOURA"),
	PAPEL("PAPEL"),
	PEDRA("PEDRA"),
	LAGARTO("LAGARTO"),
	SPOCK("SPOCK");
	
	private final String value;

	private EMovimento(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
