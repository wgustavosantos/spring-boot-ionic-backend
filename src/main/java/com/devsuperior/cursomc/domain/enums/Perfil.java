package com.devsuperior.cursomc.domain.enums;
/* classe clonada de EstadoPagamento */
public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"), CLIENTE(2, "ROLE_CLIENTE"); /* Prefixo ROLE_ é exigência do SS */
	
	private Integer cod;
	private String descricao;

	private Perfil(Integer cod, String descricao) { // construtor de enum privado

		this.cod = cod;
		this.descricao = descricao;

	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (Perfil estadopagamento : Perfil.values()) {
			if(estadopagamento.getCod().equals(cod)) {
				return estadopagamento;
			}
		}


		throw new IllegalArgumentException("Id inválido: " + cod);

	}

}
