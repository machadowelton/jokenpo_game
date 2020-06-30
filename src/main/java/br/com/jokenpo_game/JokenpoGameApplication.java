package br.com.jokenpo_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class JokenpoGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokenpoGameApplication.class, args);
	}

}
