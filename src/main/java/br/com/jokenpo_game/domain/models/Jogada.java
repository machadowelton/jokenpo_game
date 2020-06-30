package br.com.jokenpo_game.domain.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.jokenpo_game.domain.enums.EMovimento;

@JsonIgnoreProperties(
		value = { "data" },
		allowGetters = true
)
public class Jogada {
	
	private Long id;
	
	private Jogador jogador;
	
	private EMovimento movimento;
	
	private Date data = new Date();

	public Jogada() {
		super();
	}

	public Jogada(Long id, Jogador jogador, EMovimento movimento, Date data) {
		super();
		this.id = id;
		this.jogador = jogador;
		this.movimento = movimento;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public EMovimento getMovimento() {
		return movimento;
	}

	public void setMovimento(EMovimento movimento) {
		this.movimento = movimento;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jogador == null) ? 0 : jogador.hashCode());
		result = prime * result + ((movimento == null) ? 0 : movimento.hashCode());
		return result;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogada other = (Jogada) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jogador == null) {
			if (other.jogador != null)
				return false;
		} else if (!jogador.equals(other.jogador))
			return false;
		if (movimento != other.movimento)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Jogada(id=" + id + ", jogador=" + jogador + ", movimento=" + movimento + ", data=" + data + ")";
	}
	
}
