package br.com.jokenpo_game.services;

import java.util.List;

import br.com.jokenpo_game.domain.models.Partida;

public interface IPartidaService {
	
Partida buscarPorId(Long id);
	
	List<Partida> listar();
	
	Partida iniciarPartida();
	
	Partida atualizarPartida(Long id, Partida partida);

	Long getIdJogadaEIncrementa();
	
	Partida jogar(Long id);
	
}
