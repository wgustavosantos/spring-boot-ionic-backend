package com.devsuperior.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.devsuperior.cursomc.domain.enums.Estadopagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // mapeando com herança
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer estado;

	@OneToOne
	@JoinColumn(name = "pedido_id")// PK e FK ao mesmo tempo
	@MapsId//mapeando o id da entidade Pedido, mesmo id para ambas as entidades
	private Pedido pedido;

	public Pagamento() {
	}

	public Pagamento(Estadopagamento estado, Pedido pedido) {
		this.estado = estado.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estadopagamento getEstado() {
		return Estadopagamento.toEnum(estado);
	}

	public void setEstado(Estadopagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}

}
