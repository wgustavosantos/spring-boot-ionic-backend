package com.devsuperior.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.devsuperior.cursomc.security.UserSS;

/* Método para recuperar o usuario logado */
public class UserService {

	public static UserSS authenticated() {
		
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
		
	}

}
