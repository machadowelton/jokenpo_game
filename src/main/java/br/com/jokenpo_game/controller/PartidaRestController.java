package br.com.jokenpo_game.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jokenpo_game.domain.models.Partida;
import br.com.jokenpo_game.services.IPartidaService;
import br.com.jokenpo_game.services.impl.PartidaServiceImpl;

@RestController(value = "partidas")
@RequestMapping("/partidas")
public class PartidaRestController {
	
	private final IPartidaService service;

	public PartidaRestController(PartidaServiceImpl service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public Partida buscarPorId(@PathVariable("id") Long id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping
	public List<Partida> listar() {
		return service.listar();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Partida iniciarPartida() {
		return service.iniciarPartida();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Partida jogar(@PathVariable("id") Long id) {
		return service.jogar(id);
	}

}
