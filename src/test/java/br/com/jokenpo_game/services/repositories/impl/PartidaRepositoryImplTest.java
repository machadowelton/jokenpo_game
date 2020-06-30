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

import br.com.jokenpo_game.domain.enums.EStatusPartida;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.repositories.IPartidaRepository;

@ExtendWith(SpringExtension.class)
public class PartidaRepositoryImplTest {
	
	IPartidaRepository partidaRepository;
	
	
	@BeforeEach
	public void setUp() {
		this.partidaRepository = new PartidaRepositoryImpl();
	}
	
	@Test
	@DisplayName("Deverá retornar uma Optional de partida pelo id quando existir")
	public void buscarPorIdTest() {
		Long id = 1l;
		Partida partidaIniciada = partidaRepository.iniciarPartida();
		Optional<Partida> opPartidaConsulta = partidaRepository.buscarPorId(id);
		assertThat(opPartidaConsulta.isPresent()).isTrue();
		assertThat(opPartidaConsulta.get().equals(partidaIniciada)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar uma Optional vazia partida pelo id quando existir")
	public void buscarPorIdPartidaNaoEncontradaTest() {		
		Optional<Partida> opPartidaConsulta = partidaRepository.buscarPorId(Mockito.anyLong());
		assertThat(opPartidaConsulta.isPresent()).isFalse();		
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista com 2 partidas")
	public void listarTest() {
		Partida partida1 = partidaRepository.iniciarPartida();
		Partida partida2 = partidaRepository.iniciarPartida();
		List<Partida> listaPartidasIniciadas = Arrays.asList(partida1, partida2);
		List<Partida> listagemPartidas = partidaRepository.listar();
		assertThat(listagemPartidas.size()).isEqualTo(listaPartidasIniciadas.size());
		assertThat(listaPartidasIniciadas.equals(listagemPartidas)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar uma lista vazia")
	public void listarSemPartidasTest() {		
		List<Partida> listagemPartidas = partidaRepository.listar();
		assertThat(listagemPartidas).isInstanceOf(List.class);
		assertThat(listagemPartidas.size()).isEqualTo(0);
	}
	
	
	@Test
	@DisplayName("Deverá inserir iniciar uma partida")
	public void iniciarPartidaTest() {
		Partida partidaIniciada = partidaRepository.iniciarPartida();
		Optional<Partida> partidaConsulta = partidaRepository.buscarPorId(partidaIniciada.getId());
		assertThat(partidaConsulta.isPresent()).isTrue();
		assertThat(partidaConsulta.get().equals(partidaIniciada)).isTrue();
	}
	
	@Test
	@DisplayName("Deverá atualizar uma partida")
	public void atualizarPartidaTest() {
		Partida partidaIniciada = partidaRepository.iniciarPartida();
		partidaIniciada.setStatus(EStatusPartida.FECHADA);
		Partida partidaAtualizada = partidaRepository.atualizarPartida(partidaIniciada.getId(), partidaIniciada);
		Optional<Partida> partidaConsultaAtualizada = partidaRepository.buscarPorId(partidaAtualizada.getId());
		assertThat(partidaConsultaAtualizada.isPresent()).isTrue();
		assertThat(partidaAtualizada.equals(partidaConsultaAtualizada.get())).isTrue();
	}
	
	@Test
	@DisplayName("Deverá retornar um long para incrementada jogada")
	public void getIdEIncrementaTest() {
		Long idJogada = 1l;
		Long idConsultado = partidaRepository.getIdJogadaEIncrementa();
		assertThat(idConsultado).isEqualTo(idJogada);
	}
	
}
