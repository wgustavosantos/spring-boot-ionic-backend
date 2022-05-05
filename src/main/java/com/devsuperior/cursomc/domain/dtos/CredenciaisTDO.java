package com.devsuperior.cursomc.domain.dtos;

import java.io.Serializable;

public class CredenciaisTDO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;

	public CredenciaisTDO() {
	}

	public CredenciaisTDO(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
