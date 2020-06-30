package br.com.jokenpo_game.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogada;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.IJogadaService;

@ExtendWith(SpringExtension.class)
public class JogadaServiceImplTest {
	
	
	@MockBean
	private JogadorServiceImpl jogadorService;
	
	@MockBean
	private PartidaServiceImpl partidaService;
	
	private IJogadaService service;
	
	@BeforeEach
	public void setUp() {
		this.service = new JogadaServiceImpl(jogadorService, partidaService);
	}
	
	
	@Test
	@DisplayName("Deverá retorna uma jogada pelo id quando existir")
	public void buscarPorIdEIdPartidaTest() {
		Long idJogada = 1l;		
		Jogada jogada = new Jogada();
		jogada.setId(idJogada);
		Long idPartida = 1l;
		Partida partida = new Partida();
		partida.setId(idPartida);
		partida.getJogadas().add(jogada);
		Mockito.when(partidaService.buscarPorId(idPartida)).thenReturn(partida);
		Jogada jogadaConsultada = service.buscarPorIdEIdPartida(idPartida, idJogada);
		assertThat(jogadaConsultada.equals(jogada)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá lançar um erro quando não existir a jogada")
	public void buscarPorIdEIdPartidaJogadaNaoEncontradaTest() {
		Long idJogada = 1l;		
		Jogada jogada = new Jogada();
		jogada.setId(idJogada);
		Long idPartida = 1l;
		Partida partida = new Partida();
		partida.setId(idPartida);
		partida.getJogadas().add(jogada);
		Long idJogadaConsulta = 5l;
		String mensagem = "Nenhuma jogada encontrada pelo id: " + idJogadaConsulta + " e id partida: " + idPartida;
		Mockito.when(partidaService.buscarPorId(idPartida)).thenReturn(partida);
		Throwable thr = catchThrowable(() -> {
			service.buscarPorIdEIdPartida(idJogadaConsulta, idPartida);
		});
		assertThat(thr).isInstanceOf(RecursoNaoEncontradoException.class);
		assertThat(thr.getMessage()).isEqualTo(mensagem);
	}
	
}
