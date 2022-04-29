package com.devsuperior.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment environment;
	
	private static final String [] PUBLIC_MATCHES = {
			"/h2-console/**",
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override
	protected void configure (HttpSecurity http) throws Exception{ /* Sobrescrevendo método de WebSecurityConfigurerAdapter */
		
		/*Para habilitar acesso ao banco h2 */
		if(Arrays.asList(environment.getActiveProfiles()).contains("test")) { 
			/* Verifica se o profile de teste está ativo, se sim, libera o acesso*/
			http.headers().frameOptions().disable();
		}
		
		
		http.cors().and().csrf().disable(); /* Ao chamar este método, o cors configuration source é ativado (para fins de evitar erro ao testar*/
		/* Desabilitar proteção a CSRF em sistemas stateless  */
		
		/* Autoriza os caminhos que estao no vetor, e para todo o resto exige autenticação */
		http.authorizeHttpRequests()
			.antMatchers(PUBLIC_MATCHES)
			.permitAll()
			.anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); /* Assegurar que o backend não vai criar sessão de usuario */
	}
	
	@Bean /* Acesso básico a multiplas fontes à todos os caminhos  */
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
