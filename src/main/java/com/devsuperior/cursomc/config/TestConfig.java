package com.devsuperior.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.devsuperior.cursomc.services.DBService;
import com.devsuperior.cursomc.services.EmailService;
import com.devsuperior.cursomc.services.MockEmailService;

@Configuration
@Profile("test") /*
					 * Indica que todos os "Beans" da classe vão ser ativados quando o profile de
					 * test estiver ativo no application.properties
					 */
public class TestConfig {

	@Autowired
	private DBService dbService; /* Para instanciar */

	@Bean
	public Boolean instantiateDatabase() { /* Não pode retornar nullo */

		dbService.instantiateTestDatabase(); /* Se o método fosse estático iria funcionar */

		return true;
	}

	@Bean
	public EmailService MockEmailService() {
		return new MockEmailService();
	}

	@Bean /* Bean do JavaMailSender para perfil de test */
	public JavaMailSender mailSender() {
		return new JavaMailSenderImpl();

		/*
		 * Description:
		 * 
		 * Field javaMailSender in com.devsuperior.cursomc.services.AbstractEmailService
		 * required a bean of type 'org.springframework.mail.javamail.JavaMailSender'
		 * that could not be found.
		 * 
		 * The injection point has the following annotations:
		 * - @org.springframework.beans.factory.annotation.Autowired(required=true)
		 * 
		 * 
		 * Action:
		 * 
		 * Consider defining a bean of type
		 * 'org.springframework.mail.javamail.JavaMailSender' in your configuration.
		 */
	}

}
