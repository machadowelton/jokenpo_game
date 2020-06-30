package br.com.jokenpo_game.services;

import java.util.List;

import br.com.jokenpo_game.domain.models.Jogador;

public interface IJogadorService {

	Jogador buscarPorId(Long idJogador);
	
	List<Jogador> listar();

	Jogador inserir(Jogador jogador);

	void removerPorId(Long id);

}
