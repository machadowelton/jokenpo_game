package br.com.jokenpo_game.services.repositories;

import java.util.List;
import java.util.Optional;

import br.com.jokenpo_game.domain.models.Partida;

public interface IPartidaRepository {
	
	Optional<Partida> buscarPorId(Long id);
	
	List<Partida> listar();	
	
	Partida iniciarPartida();

	Partida atualizarPartida(Long id, Partida partida);

	Long getIdJogadaEIncrementa();

}
