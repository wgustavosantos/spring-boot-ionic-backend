package com.devsuperior.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/*Filtro de autorização de token \ Busca um usuario na base e verificia se o usuario do token existe*/
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService; /* */

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		/* Captura o valor que esta definido no cabeçalho da req */

		/* P/ autorizar usuario */
		if (header != null && header.startsWith("Bearer ")) { /* Se header começa com "Bearer " */
			UsernamePasswordAuthenticationToken auth = getAuthentication(request, header.substring(7)); /* */

			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		}
		
		chain.doFilter(request, response); /* Continua o fluxo de autenticação */

	}

	/* Gera um objeto com usuario dentro a partir de um token não nullo */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
		
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
