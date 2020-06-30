package br.com.jokenpo_game.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jokenpo_game.domain.exceptions.RecursoNaoEncontradoException;
import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.impl.JogadorServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = JogadorRestController.class)
@AutoConfigureMockMvc
public class JogadorRestControllerTest {
	
	static final String JOGADOR_API = "/jogadores";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	JogadorServiceImpl service;
	
	@Test
	@DisplayName("Deve retornar um jogador pelo id e com status 200")
	public void buscarPorId() throws Exception {
		Long idJogador = 1l;
		Jogador jogador = new Jogador(idJogador, "Welton");
		BDDMockito.given(service.buscarPorId(idJogador)).willReturn(jogador);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
												.get(JOGADOR_API + "/" + idJogador);
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(idJogador))
			.andExpect(jsonPath("nome").value(jogador.getNome()));
	}
	
	@Test
	@DisplayName("Deve uma lista ou vazia ou com 1 ou mais jogadores e codigo status 200")
	public void listar() throws Exception {		
		BDDMockito.given(service.listar()).willReturn(new ArrayList<Jogador>());
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
												.get(JOGADOR_API);
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray());
	}
	
	@Test
	@DisplayName("Deverá retornar um erro 404 quando o jogador não for encontrado")
	public void buscarPorIdJogadorNaoEncontrado() throws Exception {
		Long idJogador = 1l;
		String mensagem = "Nenhum jogador encontrado pelo id: " + idJogador;
		BDDMockito.given(service.buscarPorId(Mockito.anyLong())).willThrow(new RecursoNaoEncontradoException(mensagem));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(JOGADOR_API + "/" + idJogador);
		mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("mensagem").value(mensagem))
			.andExpect(jsonPath("data").isNotEmpty());
	}
	
	
	@Test
	@DisplayName("Deverá criar um jogador  e retornar status 201")
	public void inserir() throws Exception {
		Jogador jogador = new Jogador("Welton");		
		String json = new ObjectMapper().writeValueAsString(jogador);
		Jogador jogadorSalvo = new Jogador(1l, jogador.getNome());
		BDDMockito.given(service.inserir(jogador)).willReturn(jogadorSalvo);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(JOGADOR_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		mockMvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").isNotEmpty())
			.andExpect(jsonPath("nome").value(jogador.getNome()));
	}
	
	@Test
	@DisplayName("Deverá remover um jogador por id e retornar status 204")
	public void removerPorId() throws Exception {
		Long idJogador = 1l;		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
												.delete(JOGADOR_API + "/" + idJogador);
		mockMvc.perform(request)
			.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Deverá lançar um erro 404 jogador não encontrado")
	public void removerPorIdJogadorNaoEncontrado() throws Exception {
		Long idJogador = 1l;
		String mensagem = "Nenhum jogador encontrado pelo id: " + idJogador;
		BDDMockito.doThrow(new RecursoNaoEncontradoException(mensagem)).when(service).removerPorId(idJogador);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.delete(JOGADOR_API + "/" + idJogador);
		mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("mensagem").value(mensagem))
			.andExpect(jsonPath("data").isNotEmpty());
		verify(service, times(1)).removerPorId(idJogador);
	}
	
}
