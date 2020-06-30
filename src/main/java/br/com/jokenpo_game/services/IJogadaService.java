package br.com.jokenpo_game.services;

import java.util.List;

import br.com.jokenpo_game.domain.models.Jogada;

public interface IJogadaService {
	
	Jogada buscarPorIdEIdPartida(Long id, Long idPartida);
	
	List<Jogada> listarPorIdPartida(Long idPartida);
	
	Jogada inserir(Long idPartida, Jogada jogada);
	
	void removerPorIdEIdPartida(Long id, Long idPartida);
	
}
