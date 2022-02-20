package com.devsuperior.cursomc.domain;

import java.util.Date;

import com.devsuperior.cursomc.domain.enums.Estadopagamento;

public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Date dataVencimento;
	private Date dataPagamento;

	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Estadopagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
