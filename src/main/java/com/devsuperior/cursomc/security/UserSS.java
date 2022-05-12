package com.devsuperior.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.devsuperior.cursomc.domain.enums.Perfil;

/* implements UserDetails é necessário para se trabalhar com usuarios */
public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities; /* */

	public UserSS() {
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		this.id = id;
		this.email = email;
		this.senha = senha; 
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
		/*Para perfil x do set perfis, instanciar um S.G.A passando o Role do perfil */
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	public Integer getId() {
		return this.id;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Incluir regra de negócio. Por padrão, a conta não está expirada
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// Incluir regra de negócio. Por padrão, a conta não está bloequeada
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Incluir regra de negócio. Por padrão, as crendenciais não estão expiradas
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Incluir regra de negócio. Por padrão, o usuário está ativo
		return true;
	}
	
	/*Retorna true caso um dado perfil está contido na lista de perfis de autorização do user */
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
