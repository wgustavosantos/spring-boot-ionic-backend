package com.devsuperior.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devsuperior.cursomc.services.DBService;

@Configuration
@Profile("dev") /*
				 * Indica que todos os "Beans" da classe vão ser ativados quando o profile de
				 * dev estiver ativo no application.properties
				 */
public class DevConfig {

	@Autowired
	private DBService dbService; /* Para instanciar */

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public Boolean instantiateDatabase() { /* Não pode retornar nullo */

		if (strategy.equalsIgnoreCase("create")) {
			dbService.instantiateTestDatabase(); /* Se o método fosse estático iria funcionar */
			return true;
		}

		return false;
	}

}
