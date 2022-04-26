package com.devsuperior.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.devsuperior.cursomc.domain.Pedido;


public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
