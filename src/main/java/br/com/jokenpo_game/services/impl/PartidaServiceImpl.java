package br.com.jokenpo_game.services.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jokenpo_game.domain.enums.EMovimento;
import br.com.jokenpo_game.domain.enums.EStatusPartida;
import br.com.jokenpo_game.domain.exceptions.JogadasInsuficienteException;
import br.com.jokenpo_game.domain.exceptions.PartidaFechadaException;
import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogada;
import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.IPartidaService;
import br.com.jokenpo_game.services.repositories.IPartidaRepository;
import br.com.jokenpo_game.services.repositories.impl.PartidaRepositoryImpl;

@Service
public class PartidaServiceImpl implements IPartidaService {

	private final IPartidaRepository repository;
	
	private final int QT_MIN_JOGAR = 2;
	private final List<EMovimento> TESOURA_PERDE_PARA = Arrays.asList(EMovimento.PEDRA, EMovimento.SPOCK);
	private final List<EMovimento> PEDRA_PERDE_PARA = Arrays.asList(EMovimento.PAPEL, EMovimento.SPOCK);
	private final List<EMovimento> PAPEL_PERDE_PARA = Arrays.asList(EMovimento.TESOURA, EMovimento.LAGARTO);
	private final List<EMovimento> LAGARTO_PERDE_PARA = Arrays.asList(EMovimento.TESOURA, EMovimento.PEDRA);
	private final List<EMovimento> SPOCK_PERDE_PARA = Arrays.asList(EMovimento.PAPEL, EMovimento.LAGARTO);
	
	public PartidaServiceImpl(PartidaRepositoryImpl repository) {
		this.repository = repository;
	}

	@Override
	public Partida buscarPorId(Long id) {
		return repository.buscarPorId(id)
				.map((m) -> m)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhuma partida encontrada pelo id: " + id));
	}

	@Override
	public List<Partida> listar() {
		return repository.listar();
	}

	@Override
	public Partida iniciarPartida() {
		return repository.iniciarPartida();
	}

	@Override
	public Partida atualizarPartida(Long id, Partida partida) {
		buscarPorId(id);
		return repository.atualizarPartida(id, partida);
	}

	@Override
	public Long getIdJogadaEIncrementa() {
		return repository.getIdJogadaEIncrementa();
	}

	@Override
	public Partida jogar(Long id) {
		Partida partida = buscarPorId(id);
		if(partida.getStatus() == EStatusPartida.FECHADA) 
			throw new PartidaFechadaException("Esta partida não pode receber mais jogadas");
		int qtJogadas = partida.getJogadas().size(); 
		if(qtJogadas < QT_MIN_JOGAR)
			throw new JogadasInsuficienteException("A partida não tem o minimo de jogadas para ser executada. O minimo é " + QT_MIN_JOGAR + " jogadas mas a partida contém: " + qtJogadas + " jogadas");
		Optional<Jogador> opJogador = solucacao(partida.getJogadas());
		Jogador jogadorVencedor = opJogador.isPresent() ? opJogador.get() : null;
		partida.setJogadorVencedor(jogadorVencedor);
		partida.setDataFim(new Date());
		partida.setStatus(EStatusPartida.FECHADA);
		return partida;
	}
	
	
	//Solução com sucesso para 2 jogadores, para 3 quando o vencedor é o primeiro a jogar.
	private Optional<Jogador> solucacao(List<Jogada> jogadas) {
		List<Jogada> restante = jogadas.stream().collect(Collectors.toList());
		List<Jogada> jogadas_aux = jogadas.stream().collect(Collectors.toList());
		for(Jogada jogada: jogadas) {
			for(Jogada jogada_aux: jogadas_aux) {
				if(jogada.equals(jogada_aux)) continue;
				switch(jogada.getMovimento()) {
				case TESOURA:
					if(TESOURA_PERDE_PARA.contains(jogada_aux.getMovimento()))
						if(restante.size() >0 ) restante.remove(jogada);
					break;
				case PEDRA:
					if(PEDRA_PERDE_PARA.contains(jogada_aux.getMovimento()))
						if(restante.size() >0 ) restante.remove(jogada);
					break;
				case PAPEL:
					if(PAPEL_PERDE_PARA.contains(jogada_aux.getMovimento()))
						if(restante.size() >0 ) restante.remove(jogada);
					break;
				case LAGARTO:
					if(LAGARTO_PERDE_PARA.contains(jogada_aux.getMovimento()))
						if(restante.size() >0 ) restante.remove(jogada);
					break;
				case SPOCK:
					if(SPOCK_PERDE_PARA.contains(jogada_aux.getMovimento()))
						if(restante.size() >0 ) restante.remove(jogada);
					break;
				}
			}
		}
		if(restante.size() != 1) return Optional.empty();
		return Optional.of(restante.stream().findFirst().get().getJogador());
	}
}
