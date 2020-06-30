package br.com.jokenpo_game.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jokenpo_game.domain.models.Jogador;
import br.com.jokenpo_game.services.IJogadorService;
import br.com.jokenpo_game.services.impl.JogadorServiceImpl;

@RestController(value = "jogadores")
@RequestMapping("/jogadores")
public class JogadorRestController {
		
	private final IJogadorService service;
	
	public JogadorRestController(JogadorServiceImpl service) {
		this.service = service;
	}



	@GetMapping("/{id}")
	public Jogador buscarPorId(@PathVariable("id") Long id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping
	public List<Jogador> listar() {
		return service.listar();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Jogador inserir(@RequestBody Jogador jogador) {
		return service.inserir(jogador);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPorId(@PathVariable("id") Long id) {
		service.removerPorId(id);
	}
	
}
