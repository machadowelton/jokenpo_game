package br.com.jokenpo_game.services.repositories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.repositories.IPartidaRepository;

@Repository
public class PartidaRepositoryImpl implements IPartidaRepository {
	
	
	private final AtomicLong idIncrement = new AtomicLong(1l);

	private final AtomicLong idJogadaIncrement = new AtomicLong(1l);
	
	private final Map<Long, Partida> partidas = new HashMap<Long, Partida>();

	@Override
	public Optional<Partida> buscarPorId(Long id) {
		if(partidas.containsKey(id))
			return Optional.of(partidas.get(id));
		return Optional.empty();
	}

	@Override
	public List<Partida> listar() {
		return partidas.entrySet().stream()
				.map((m) -> {
					Partida partida = 
							new Partida(
									m.getKey(), 
									m.getValue().getJogadas(), 
									m.getValue().getJogadorVencedor(), 
									m.getValue().getDataInicio(), 
									m.getValue().getDataFim(), 
									m.getValue().getStatus());
					return partida;
				})
				.collect(Collectors.toList());
	}

	@Override
	public Partida iniciarPartida() {
		Long idPartida = idIncrement.getAndIncrement();
		Partida partida =  new Partida();
		partida.setId(idPartida);
		partidas.put(idPartida, partida);
		Partida partidaCriada = partidas.get(idPartida);
		return partidaCriada;
	}

	@Override
	public Partida atualizarPartida(Long id, Partida partida) {
		partidas.remove(partida.getId());
		partidas.put(partida.getId(), partida);
		Partida partidaAtualizada = partidas.get(partida.getId()); 
		return partidaAtualizada;
	}

	@Override
	public Long getIdJogadaEIncrementa() {
		return idJogadaIncrement.getAndIncrement();
	}

}
