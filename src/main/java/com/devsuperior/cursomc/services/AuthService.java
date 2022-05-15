package com.devsuperior.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.repositories.ClienteRepository;
import com.devsuperior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
@Component
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	public void sendNewPassword(String email) {

		if (email != null) {

			Cliente cliente = clienteRepository.findByEmail(email);

			if (cliente == null) {
				throw new ObjectNotFoundException("Email não encontrado");
			}

			String newPass = newPassword();

			cliente.setSenha(encoder.encode(newPass));

			clienteRepository.save(cliente);

			emailService.sendNewPasswordEmail(cliente, newPass);

		}

	}

	private String newPassword() {

		char[] vet = new char[10];

		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() { /* gera caractere alfanumerico aleatorio */

		Random rand = new Random();

		int cont = rand.nextInt(3); /* Sempre que randomChar for invocado, cont muda entre 0 ate 2 */

		if (cont == 0) { // Para caractere numerico
			char ch = (char) (rand.nextInt(10) + 48); // Na tbl unicode, 48 é o primeiro digito de numero
			return ch;

		} else if (cont == 1) { // Para caractere letra maiuscula

			char ch = (char) (rand.nextInt(26) + 65); // Na tbl unicode, 65 é o primeiro digito de letra maiuscula
			return ch;
		} else { // Para caractere letra minuscula

			char ch = (char) (rand.nextInt(26) + 97); // Na tbl unicode, 97 é o primeiro digito de letra minuscula
			return ch;

		}
	}

}
