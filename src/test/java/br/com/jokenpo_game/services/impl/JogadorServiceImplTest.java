package br.com.jokenpo_game.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.IJogadorService;
import br.com.jokenpo_game.services.repositories.impl.JogadorRepositoryImpl;

@ExtendWith(SpringExtension.class)
public class JogadorServiceImplTest {
	
	
	@MockBean
	private JogadorRepositoryImpl jogadorRepository;
	
	private IJogadorService service;
	
	@BeforeEach
	public void setUp() {
		service = new JogadorServiceImpl(jogadorRepository);
	}
	
	
	@Test
	@DisplayName("Deverá retornar um jogador pelo id")
	public void buscarPorIdTest() {
		Long id = 1l;
		Jogador jogadorMock = new Jogador(id, "Jogador1");
		Mockito.when(jogadorRepository.buscarPorId(id)).thenReturn(Optional.of(jogadorMock));
		Jogador jogadorConsulta = service.buscarPorId(id);
		assertThat(jogadorConsulta.equals(jogadorMock)).isTrue();
		verify(jogadorRepository,times(1)).buscarPorId(id);
	}
	
	@Test
	@DisplayName("Deverá retornar um jogador pelo id")
	public void buscarPorIdJogadorNaoEncontradoTest() {
		Long id = 1l;
		Mockito.when(jogadorRepository.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());
		Throwable thr = catchThrowable(() -> {
			service.buscarPorId(id);
		});
		assertThat(thr).isInstanceOf(RecursoNaoEncontradoException.class);
		assertThat(thr.getMessage()).isEqualTo("Nenhum jogador encontrado pelo id: " + id);
		verify(jogadorRepository,times(1)).buscarPorId(id);
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista de jogadores")
	public void listarTest() {		
		Jogador jogador1 = new Jogador(1l, "jogador1");
		Jogador jogador2 = new Jogador(2l, "jogador2");
		List<Jogador> jogadores = Arrays.asList(jogador1, jogador2);
		Mockito.when(jogadorRepository.listar()).thenReturn(jogadores);
		List<Jogador> listaJogadores = service.listar();
		assertThat(listaJogadores.equals(jogadores)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista vazia de jogadores")
	public void listarSemJogadoresTest() {		
		List<Jogador> list = new ArrayList<Jogador>();
		Mockito.when(jogadorRepository.listar()).thenReturn(list);
		List<Jogador> listaJogadores = service.listar();
		assertThat(listaJogadores).isInstanceOf(List.class);
		assertThat(listaJogadores.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Deverá inserir um jogador")
	public void inserirTest() {		
		Jogador jogador = new Jogador("jogador1");
		Jogador jogadorMock = new Jogador(1l, "jogador1");
		Mockito.when(jogadorRepository.inserir(jogador)).thenReturn(jogadorMock);
		Jogador jogadorSalvo = service.inserir(jogador);
		assertThat(jogadorSalvo.equals(jogadorMock)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá remover um usuário por id quando existir")
	public void removerPorIdTest() {		
		Long id = 1l;
		Mockito.when(jogadorRepository.removerPorId(Mockito.anyLong())).thenReturn(Boolean.TRUE);
		service.removerPorId(id);
		verify(jogadorRepository, times(1)).removerPorId(id);
	}
	
	@Test
	@DisplayName("Deverá lançar uma exceção quando jogador não for encontrado")
	public void removerPorIdJogadorNaoEncontradoTest() {		
		Long id = 1l;
		Mockito.when(jogadorRepository.removerPorId(Mockito.anyLong())).thenReturn(Boolean.FALSE);
		Throwable thr = catchThrowable(() -> {
			service.removerPorId(id);
		});
		assertThat(thr).isInstanceOf(RecursoNaoEncontradoException.class);
		assertThat(thr.getMessage()).isEqualTo("Nenhum jogador encontrado pelo id: " + id);
		verify(jogadorRepository, times(1)).removerPorId(id);
	}
	
}
