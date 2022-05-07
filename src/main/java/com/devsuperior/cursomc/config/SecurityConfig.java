package com.devsuperior.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String [] PUBLIC_MATCHES = {
			"/h2-console/**",
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override /* Método sobrescrevido */
	protected void configure(HttpSecurity http) throws Exception {
		
		/* .cors() chama o corsConfigurationSource e define config básicas */
		http.cors().and().csrf().disable(); /* Ao chamar este método, o cors configuration source é ativado (para fins de evitar erro ao testar*/
		/* Desabilitar proteção a CSRF em sistemas stateless, pois nosso sistema não armazena a sessão  */

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); /* Assegurar que o backend não vai criar sessão de usuario */
		
		http.authorizeHttpRequests()
			.antMatchers(PUBLIC_MATCHES).permitAll() /*Permitindo todos os endpoints declarados no vetor */
			.anyRequest().authenticated();/* Para todo o resto exigir autenticação */
	}
	
	@Bean /* Acesso básico a multiplas fontes à todos os caminhos  */
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
