package com.devsuperior.cursomc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date instante;

	@OneToOne
	private Pagamento pagamento;

	@OneToOne
	private Endereco enderecoDeEntrega;

	@ManyToOne
	private Cliente cliente;

	public Pedido() {
	}

	public Pedido(Integer id, Date instante, Pagamento pagamento, Endereco enderecoDeEntrega, Cliente cliente) {
		this.id = id;
		this.instante = instante;
		this.pagamento = pagamento;
		this.enderecoDeEntrega = enderecoDeEntrega;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

}
