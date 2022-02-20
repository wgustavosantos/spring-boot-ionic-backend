package com.devsuperior.cursomc.domain.enums;

public enum Estadopagamento {
	
	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Parcelado");
	

	private Integer cod;
	private String descricao;

	private Estadopagamento(Integer cod, String descricao) { // construtor de enum privado

		this.cod = cod;
		this.descricao = descricao;

	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Estadopagamento toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (Estadopagamento estadopagamento : Estadopagamento.values()) {
			if(estadopagamento.getCod().equals(cod)) {
				return estadopagamento;
			}
		}


		throw new IllegalArgumentException("Id inválido: " + cod);

	}

}
