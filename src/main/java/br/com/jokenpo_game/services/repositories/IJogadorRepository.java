package br.com.jokenpo_game.services.repositories;

import java.util.List;
import java.util.Optional;

import br.com.jokenpo_game.domain.models.Jogador;

public interface IJogadorRepository {
	
	Optional<Jogador> buscarPorId(Long id);
	
	List<Jogador> listar();
	
	Jogador inserir(Jogador jogador);
	
	boolean removerPorId(Long id);
	
}
