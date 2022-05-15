package com.devsuperior.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devsuperior.cursomc.security.JWTAuthenticationFilter;
import com.devsuperior.cursomc.security.JWTAuthorizationFilter;
import com.devsuperior.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) /* Para add anotações de pré autorização em endpoints */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTUtil jWTUtil;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDetailsService userDetailsService; /* Busca uma implementação da inferface, no caso UserDetailsServiceImpl */
	
	private static final String [] PUBLIC_MATCHES = {
			"/h2-console/**",
			
	};
	
	/*nao logado -Caminhos somente para leitura (somente get, não permite outros add com post)*/
	private static final String [] PUBLIC_MATCHES_GET = {
			"/produtos/**",
			"/categorias/**",
	};
	
	/*nao logado - Caminhos somente para inserção (somente post, cadastro de usuario nao logado)*/
	private static final String [] PUBLIC_MATCHES_POST = {
			"/clientes/**",
			"/auth/forgot/**"
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
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET)
			.permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHES_POST)
			.permitAll()
			.anyRequest().authenticated();
		
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jWTUtil)); /* authenticationManager() vem do polimorfismo   */
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jWTUtil, userDetailsService));		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); /* Assegurar que o backend não vai criar sessão de usuario */
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean /* Acesso básico a multiplas fontes à todos os caminhos  */
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean /* Disponivel um bean, um componente BCPE para injetar em qualquer classe do sistema */
	BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
}
