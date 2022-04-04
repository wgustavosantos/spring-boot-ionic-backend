package com.devsuperior.cursomc.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.cursomc.domain.ItemPedido;
import com.devsuperior.cursomc.domain.PagamentoComBoleto;
import com.devsuperior.cursomc.domain.Pedido;
import com.devsuperior.cursomc.domain.enums.Estadopagamento;
import com.devsuperior.cursomc.repositories.ItemPedidoRepository;
import com.devsuperior.cursomc.repositories.PagamentoRepository;
import com.devsuperior.cursomc.repositories.PedidoRepository;
import com.devsuperior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ". Tipo: " + Pedido.class));

	}

	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(LocalDateTime.now());
		obj.getPagamento().setEstado(Estadopagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,
					Date.from(obj.getInstante().atZone(ZoneId.systemDefault()).toInstant()));
		}
		
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {

			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPedido(obj);
			
		}

		itemPedidoRepository.saveAll(obj.getItens());

		return obj;

	}

}
