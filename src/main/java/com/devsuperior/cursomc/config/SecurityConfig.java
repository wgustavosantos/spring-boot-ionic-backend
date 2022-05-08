package com.devsuperior.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment environment;
	
	
	private static final String [] PUBLIC_MATCHES = {
			"/h2-console/**",
	};
	
	/*Caminhos somente para leitura (somente get, não permite outros add com post)*/
	private static final String [] PUBLIC_MATCHES_GET = {
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override /* Método sobrescrevido */
	protected void configure(HttpSecurity http) throws Exception {
		
		/*Para habilitar acesso ao banco h2 - Verifica se o profile de teste está ativo, se sim, libera o acesso*/
		if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		
		/* .cors() chama o corsConfigurationSource e define config básicas */
		http.cors().and().csrf().disable(); /* Ao chamar este método, o cors configuration source é ativado (para fins de evitar erro ao testar*/
		/* Desabilitar proteção a CSRF em sistemas stateless, pois nosso sistema não armazena a sessão  */

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); /* Assegurar que o backend não vai criar sessão de usuario */
		
		http.authorizeHttpRequests()
			.antMatchers(PUBLIC_MATCHES).permitAll() /*Permitindo todos os endpoints declarados no vetor */
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll() /* Somente get nos endpoints que estao no vetor, proibido fazer post, put, delete... */
			.anyRequest().authenticated();/* Para todo o resto exigir autenticação */
	}
	
	@Bean /* Acesso básico a multiplas fontes à todos os caminhos  */
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean /* Metodo para encriptar a senha do usuario */
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}

}
