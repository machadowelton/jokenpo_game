package br.com.jokenpo_game.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.IJogadorService;
import br.com.jokenpo_game.services.repositories.IJogadorRepository;
import br.com.jokenpo_game.services.repositories.impl.JogadorRepositoryImpl;

@Service
public class JogadorServiceImpl implements IJogadorService {

	
	private final IJogadorRepository repository;		
	
	public JogadorServiceImpl(final JogadorRepositoryImpl jogadorRepositoryImpl) {
		this.repository = jogadorRepositoryImpl;
	}

	@Override
	public Jogador buscarPorId(Long idJogador) {
		return repository.buscarPorId(idJogador)
							.map((m) -> m)
							.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum jogador encontrado pelo id: " + idJogador));
	}

	@Override
	public Jogador inserir(Jogador jogador) {
		return repository.inserir(jogador);
	}

	@Override
	public void removerPorId(Long id) {
		if(!repository.removerPorId(id))
			throw new RecursoNaoEncontradoException("Nenhum jogador encontrado pelo id: " + id); 
	}

	@Override
	public List<Jogador> listar() {
		return repository.listar();
	}

}
