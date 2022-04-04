package com.devsuperior.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devsuperior.cursomc.services.DBService;

@Configuration
@Profile("test") /* Indica que todos os "Beans" da classe vão ser ativados quando o profile de test estiver ativo no application.properties */
public class TestConfig {
	
	@Autowired
	private DBService dbService; /* Para instanciar */
	
	@Bean
	public Boolean instantiateDatabase() { /* Não pode retornar nullo */
		
		dbService.instantiateTestDatabase(); /*Se o método fosse estático iria funcionar */
		
		return true;
	}

}
