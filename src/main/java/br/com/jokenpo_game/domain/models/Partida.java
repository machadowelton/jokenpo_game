package br.com.jokenpo_game.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.jokenpo_game.domain.enums.EStatusPartida;

@JsonIgnoreProperties(value = {"dataFim", "status"},
	allowGetters = true)
public class Partida {
	
private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<Jogada> jogadas = new ArrayList<>();
	
	@JsonInclude(Include.NON_NULL)
	private Jogador jogadorVencedor;
	
	private Date dataInicio = new Date();
	
	@JsonInclude(Include.NON_NULL)
	private Date dataFim;
	
	private EStatusPartida status = EStatusPartida.ABERTA;

	public Partida() {
		super();
	}

	public Partida(Long id, List<Jogada> jogadas, Jogador jogadorVencedor, Date dataInicio, Date dataFim,
			EStatusPartida status) {
		super();
		this.id = id;
		this.jogadas = jogadas;
		this.jogadorVencedor = jogadorVencedor;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.status = status;
	}
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Jogada> getJogadas() {
		return jogadas;
	}

	public void setJogadas(List<Jogada> jogadas) {
		this.jogadas = jogadas;
	}

	public Jogador getJogadorVencedor() {
		return jogadorVencedor;
	}

	public void setJogadorVencedor(Jogador jogadorVencedor) {
		this.jogadorVencedor = jogadorVencedor;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public EStatusPartida getStatus() {
		return status;
	}

	public void setStatus(EStatusPartida status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jogadas == null) ? 0 : jogadas.hashCode());
		result = prime * result + ((jogadorVencedor == null) ? 0 : jogadorVencedor.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Partida other = (Partida) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jogadas == null) {
			if (other.jogadas != null)
				return false;
		} else if (!jogadas.equals(other.jogadas))
			return false;
		if (jogadorVencedor == null) {
			if (other.jogadorVencedor != null)
				return false;
		} else if (!jogadorVencedor.equals(other.jogadorVencedor))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Partida(id=" + id + ", jogadas=" + jogadas + ", jogadorVencedor=" + jogadorVencedor + ", dataInicio="
				+ dataInicio + ", dataFim=" + dataFim + ", status=" + status + ")";
	}
	
}
