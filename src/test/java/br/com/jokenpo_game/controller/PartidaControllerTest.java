package br.com.jokenpo_game.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.jokenpo_game.domain.enums.EStatusPartida;
import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.impl.PartidaServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PartidaRestController.class)
@AutoConfigureMockMvc
public class PartidaControllerTest {
	
	
	static final String PARTIDA_API = "/partidas";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	PartidaServiceImpl service;
	
	@Test
	@DisplayName("Deve retornar uma partida pelo id e com status 200")
	public void buscarPorIdTest() throws Exception {
		Partida partidaMock = new Partida();
		Long idPartida = 1l;
		partidaMock.setId(idPartida);
		BDDMockito.given(service.buscarPorId(idPartida)).willReturn(partidaMock);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PARTIDA_API + "/" + idPartida);
		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").isNotEmpty())
			.andExpect(jsonPath("status").value(EStatusPartida.ABERTA.name()))
			.andExpect(jsonPath("dataInicio").isNotEmpty());
	}	
	
}
