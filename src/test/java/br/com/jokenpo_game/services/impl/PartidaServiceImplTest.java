package br.com.jokenpo_game.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.jokenpo_game.domain.enums.EStatusPartida;
import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.IPartidaService;
import br.com.jokenpo_game.services.repositories.impl.PartidaRepositoryImpl;

@ExtendWith(SpringExtension.class)
public class PartidaServiceImplTest {
	
	
	@MockBean
	private PartidaRepositoryImpl partidaRepository;
	
	private IPartidaService service;
	
	
	@BeforeEach
	public void setUp() {
		this.service = new PartidaServiceImpl(partidaRepository);
	}
	
	@Test
	@DisplayName("Deverá retornar uma partida pelo id quando existir")
	public void buscarPorIdTest() {
		Long id = 1l;
		Partida partidaMock = new Partida();
		partidaMock.setId(id);
		Mockito.when(partidaRepository.buscarPorId(id)).thenReturn(Optional.of(partidaMock));
		Partida partida = service.buscarPorId(id);
		assertThat(partida.equals(partidaMock)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar um erro quando nao encontrar a partida pelo id")
	public void buscarPorIdPartidaNaoEncontradaTest() {
		Long id = 1l;
		String mensagem = "Nenhuma partida encontrada pelo id: " + id;
		Mockito.when(partidaRepository.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());
		Throwable thr = catchThrowable(() -> {
			service.buscarPorId(id);
		});
		assertThat(thr).isInstanceOf(RecursoNaoEncontradoException.class);
		assertThat(thr.getMessage()).isEqualTo(mensagem);
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista de partidas maior que zero")
	public void listarTest() {
		Partida partida1 = service.iniciarPartida();
		Partida partida2 = service.iniciarPartida();
		List<Partida> partidasMock = Arrays.asList(partida1, partida2);
		Mockito.when(partidaRepository.listar()).thenReturn(partidasMock);
		List<Partida> partidasListagem = service.listar();
		assertThat(partidasListagem.size()).isGreaterThan(0);
		assertThat(partidasListagem.equals(partidasMock)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista vazia")
	public void listarSemPartidasTest() {
		Mockito.when(partidaRepository.listar()).thenReturn(new ArrayList<Partida>());
		List<Partida> partidasListagem = service.listar();
		assertThat(partidasListagem).isInstanceOf(List.class);
		assertThat(partidasListagem.size()).isEqualTo(0);
	}
	
	
	@Test
	@DisplayName("Deverá iniciar uma partida e retornar uma partida")
	public void iniciarPartidaTest() {
		Partida partidaMock = new Partida();
		partidaMock.setId(1l);		
		Mockito.when(partidaRepository.iniciarPartida()).thenReturn(partidaMock);
		Partida partidaIniciada = service.iniciarPartida();
		assertThat(partidaIniciada.equals(partidaMock)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá atualizar uma partida pelo id e com os dados da partida")
	public void atualizarPartidaTest() {
		Partida partidaMock = new Partida();
		partidaMock.setId(1l);
		Mockito.when(partidaRepository.buscarPorId(partidaMock.getId())).thenReturn(Optional.of(partidaMock));
		Partida partidaAtualizarMock = partidaMock;
		partidaAtualizarMock.setStatus(EStatusPartida.FECHADA);
		Partida partidaAtualizar = partidaMock;
		partidaAtualizar.setStatus(EStatusPartida.FECHADA);
		Mockito.when(partidaRepository.atualizarPartida(partidaAtualizarMock.getId(), partidaAtualizar)).thenReturn(partidaAtualizarMock);
		Partida partidaAtualizada = service.atualizarPartida(partidaAtualizar.getId(), partidaAtualizar);
		assertThat(partidaAtualizada.equals(partidaAtualizarMock));
	}
	
	@Test
	@DisplayName("Deverá lançar um erro ao tentar atualizar partida inexistente")
	public void atualizarPartidaNaoEncontradaTest() {
		Long id = 1l;
		Partida partida = new Partida();
		partida.setId(id);
		String mensagem = "Nenhuma partida encontrada pelo id: " + id;
		Mockito.when(partidaRepository.buscarPorId(id)).thenReturn(Optional.empty());
		Throwable thr = catchThrowable(() -> {
			service.atualizarPartida(id, partida);			
		});
		assertThat(thr).isInstanceOf(RecursoNaoEncontradoException.class);
		assertThat(thr.getMessage()).isEqualTo(mensagem);
	}
	
}
