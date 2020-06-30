package br.com.jokenpo_game.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo_game.domain.enums.EStatusPartida;
import br.com.jokenpo_game.domain.exceptions.PartidaFechadaException;
import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogada;
import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.IJogadaService;
import br.com.jokenpo_game.services.IJogadorService;
import br.com.jokenpo_game.services.IPartidaService;

@Service
public class JogadaServiceImpl implements IJogadaService {

	
	private final IJogadorService jogadorService;
	
	private final IPartidaService partidaService;
	
	
	public JogadaServiceImpl(JogadorServiceImpl jogadorService, PartidaServiceImpl partidaService) {
		this.jogadorService = jogadorService;
		this.partidaService = partidaService;
	}

	@Override
	public Jogada buscarPorIdEIdPartida(Long id, Long idPartida) {
		return partidaService.buscarPorId(idPartida).getJogadas().stream()
				.filter((j) -> j.getId() == id)			
				.findAny()
				.map((m) -> m)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhuma jogada encontrada pelo id: " + id + " e id partida: " + idPartida));
	}

	@Override
	public List<Jogada> listarPorIdPartida(Long idPartida) {
		return partidaService.buscarPorId(idPartida).getJogadas();
	}

	@Override
	public Jogada inserir(Long idPartida, Jogada jogada) {
		Jogador jogador = jogadorService.buscarPorId(jogada.getJogador().getId());
		Partida partida = partidaService.buscarPorId(idPartida);
		if(partida.getStatus() == EStatusPartida.FECHADA)
			throw new PartidaFechadaException("Esta partida não pode receber mais jogadas");
		Long idJogada = partidaService.getIdJogadaEIncrementa();		
		Jogada novaJogada = new Jogada(idJogada, jogador, jogada.getMovimento(), jogada.getData());
		partida.getJogadas().removeIf((j) -> {
			return j.getJogador().getId() == novaJogada.getJogador().getId();
		});			
		partida.getJogadas().add(novaJogada);
		Partida partidaAtualizada = partidaService.atualizarPartida(idPartida, partida);		
		return partidaAtualizada.getJogadas().stream()
					.filter((j) -> j.getId() == idJogada)
					.findFirst()
					.get();
	}

	@Override
	public void removerPorIdEIdPartida(Long id, Long idPartida) {
		Partida partida = partidaService.buscarPorId(idPartida);
		if(partida.getStatus() == EStatusPartida.FECHADA)
			throw new PartidaFechadaException("Esta partida esta fechada");
		boolean removeu = partida.getJogadas().removeIf((j) -> j.getId() == id);
		if(!removeu)
			throw new RecursoNaoEncontradoException("Nenhuma jogada encontrada pelo id: " + id + " e id partida: " + idPartida);
		partidaService.atualizarPartida(partida.getId(), partida);
	}

}
