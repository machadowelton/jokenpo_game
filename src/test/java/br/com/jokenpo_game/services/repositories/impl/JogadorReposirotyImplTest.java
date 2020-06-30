package br.com.jokenpo_game.services.repositories.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.repositories.IJogadorRepository;

@ExtendWith(SpringExtension.class)
public class JogadorReposirotyImplTest {
	
	
	private IJogadorRepository jogadorRepository;
	
	@BeforeEach
	public void setUp() {
		this.jogadorRepository = new JogadorRepositoryImpl();
	}
	
	
	@Test
	@DisplayName("Deverá retornar um Optional<Jogador> com jogador presente")
	public void buscarPorId() {
		Jogador jogador = new Jogador("Welton");
		Jogador jogadorSalvo = jogadorRepository.inserir(jogador);
		Optional<Jogador> opJogador = jogadorRepository.buscarPorId(jogadorSalvo.getId());
		assertThat(opJogador.isPresent()).isTrue();
		assertThat(opJogador.get()).isEqualTo(jogadorSalvo);
	}
	
	@Test
	@DisplayName("Deverá retornar um Optional.empty")
	public void buscarPorIdJogadorNaoEncontrado() {		
		Optional<Jogador> opJogador = jogadorRepository.buscarPorId(Mockito.anyLong());
		assertThat(opJogador.isPresent()).isFalse();		
	}
	
	@Test
	@DisplayName("Deverá um inserir o jogador no hashMap e retornar um jogador com id")
	public void inserirTest() {
		Jogador jogador = new Jogador("Jogador1");
		Long id = 1l;
		Jogador jogadorDeveRetornar = new Jogador(id, jogador.getNome());
		Jogador jogadorSalvo = jogadorRepository.inserir(jogador);
		Jogador jogadorConsulta = jogadorRepository.buscarPorId(jogadorSalvo.getId()).get();
		assertThat(jogadorSalvo).isInstanceOf(Jogador.class);
		assertThat(jogadorSalvo).isEqualTo(jogadorDeveRetornar);		
		assertThat(jogadorConsulta).isEqualTo(jogadorSalvo);		
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista de Jogadores")
	public void listarTest() {
		Jogador jogador1 = new Jogador("jogador1");
		Jogador jogador2 = new Jogador("jogador2");
		Jogador jogadorSalvo1 = jogadorRepository.inserir(jogador1);
		Jogador jogadorSalvo2 = jogadorRepository.inserir(jogador2);
		List<Jogador> jogadoresSalvo = Arrays.asList(jogadorSalvo1, jogadorSalvo2);
		List<Jogador> listagemJogadores = jogadorRepository.listar();
		assertThat(listagemJogadores).isInstanceOf(List.class);
		assertThat(listagemJogadores.size()).isGreaterThan(0);
		assertThat(listagemJogadores.equals(jogadoresSalvo)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista vazia de Jogadores")
	public void listarSemJogadorTest() {		
		List<Jogador> listagemJogadores = jogadorRepository.listar();
		assertThat(listagemJogadores).isInstanceOf(List.class);
		assertThat(listagemJogadores.size()).isEqualTo(0);
	}
	
	@Test
	@DisplayName("Deverá true ao remover o jogador pelo id")
	public void removerPorIdTest() {		
		Jogador jogador = new Jogador("Welton");
		Jogador jogadorSalvo = jogadorRepository.inserir(jogador);
		boolean removeu = jogadorRepository.removerPorId(jogadorSalvo.getId());
		Optional<Jogador> jogadorConsulta = jogadorRepository.buscarPorId(jogadorSalvo.getId());
		assertThat(removeu).isTrue();
		assertThat(jogadorConsulta.isPresent()).isFalse();
	}
	
	@Test
	@DisplayName("Deverá retornar false ao tentar remover jogador inexistente")
	public void removerPorIdJogadorNaoEncontradoTest() {			
		boolean removeu = jogadorRepository.removerPorId(Mockito.anyLong());
		assertThat(removeu).isFalse();		
	}
	
}
