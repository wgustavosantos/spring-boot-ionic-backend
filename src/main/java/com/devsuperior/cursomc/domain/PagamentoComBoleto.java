package com.devsuperior.cursomc.domain;

import java.util.Date;

import com.devsuperior.cursomc.domain.enums.Estadopagamento;

public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Estadopagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

}
