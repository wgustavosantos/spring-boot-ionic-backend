package com.devsuperior.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.devsuperior.cursomc.services.DBService;
import com.devsuperior.cursomc.services.EmailService;
import com.devsuperior.cursomc.services.MockEmailService;

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
	
	@Bean
	public EmailService MockEmailService() {
		return new MockEmailService();
	}
	
	@Bean /* Bean do JavaMailSender para perfil de test*/
	public MailSender mailSender() {
		return new JavaMailSenderImpl();
	}

}
