package com.devsuperior.cursomc.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;

import com.devsuperior.cursomc.domain.enums.Estadopagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataVencimento;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataPagamento;

	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Estadopagamento estado, Pedido pedido, LocalDateTime dataVencimento, LocalDateTime dataPagamento) {
		super(estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
