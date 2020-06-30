package br.com.jokenpo_game.services.repositories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.repositories.IJogadorRepository;

@Repository
public class JogadorRepositoryImpl implements IJogadorRepository {
	
	private final AtomicLong idIncrement = new AtomicLong(1l);

	private final Map<Long, Jogador> jogadores = new HashMap<Long, Jogador>();

	@Override
	public Optional<Jogador> buscarPorId(Long id) {
		return jogadores.containsKey(id) ? 
				Optional.of(jogadores.get(id)) 
				: Optional.empty();
	}

	@Override
	public List<Jogador> listar() {
		if(jogadores.isEmpty()) return new ArrayList<>();
		return jogadores.entrySet().stream()
				.map((m) -> new Jogador(m.getKey(), m.getValue().getNome()))
				.collect(Collectors.toList());
	}

	@Override
	public Jogador inserir(Jogador jogador) {
		Long idJogador = idIncrement.getAndIncrement();
		Jogador novoJogador = new Jogador(idJogador, jogador.getNome());
		jogadores.put(idJogador, novoJogador);
		Jogador jogadorSalvo = jogadores.get(idJogador);
		return jogadorSalvo;
	}

	@Override
	public boolean removerPorId(Long id) {
		if(!jogadores.containsKey(id))
			return false;
		jogadores.remove(id);
		return true;
	}

}
