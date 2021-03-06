package com.devsuperior.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime instante;

	@OneToOne(cascade=CascadeType.ALL, mappedBy = "pedido") // na entidade Pagamento, o id será o mesmo
	private Pagamento pagamento;

	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {
	}

	public Pedido(Integer id, LocalDateTime instante, Endereco enderecoDeEntrega, Cliente cliente) {
		this.id = id;
		this.instante = instante;
		this.enderecoDeEntrega = enderecoDeEntrega;
		this.cliente = cliente;
	}
	
	public Double getValorTotal() {
		
		Double soma = 0.0;
		
		for(ItemPedido x : this.itens ) {
			soma += x.getSubTotal();
		}
		
		return soma;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public void setInstante(LocalDateTime instante) {
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
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
		
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(dtf.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situação de pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\nDetalhes:\n");
		
		for(ItemPedido ip: getItens()) {
			builder.append(ip.toString());
		}
		
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		
		return builder.toString();
	}
	

}
