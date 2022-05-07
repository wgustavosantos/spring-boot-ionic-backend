package com.devsuperior.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.devsuperior.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}") /* Faz o frmwrk capturar o valor da propriedade no app.properties */
	private String sender;

	@Autowired
	private TemplateEngine templateEngine; /* Para processar o template do thymeleaf */
	
	@Autowired(required = false)
	private JavaMailSender javaMailSender; /* Injetar na classe para transformar um objeto Pedido em MimeMessage  */

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {

		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(sm); /* da classe EmailService */
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();
		context.setVariable("pedido", obj);

		return templateEngine.process("email/confirmacaoPedido",
				context); /* Por padrão, o Thymeleaf busca na pasta resources/templates */

	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm); /* da classe EmailService */
			
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); /* true para objeto multiparte*/
		mimeMessageHelper.setTo(pedido.getCliente().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(pedido), true); /* boolean afirmando que eh um html */

		return mimeMessage;
	}

}
