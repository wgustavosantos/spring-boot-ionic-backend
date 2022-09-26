package com.devsuperior.cursomc.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.domain.ItemPedido;
import com.devsuperior.cursomc.domain.PagamentoComBoleto;
import com.devsuperior.cursomc.domain.Pedido;
import com.devsuperior.cursomc.domain.enums.Estadopagamento;
import com.devsuperior.cursomc.repositories.ItemPedidoRepository;
import com.devsuperior.cursomc.repositories.PagamentoRepository;
import com.devsuperior.cursomc.repositories.PedidoRepository;
import com.devsuperior.cursomc.security.UserSS;
import com.devsuperior.cursomc.services.exceptions.AuthorizationException;
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

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private MockEmailService mockEmailService;

	@Autowired
	private SmtpEmailService smtpEmailService;

	@Value("${spring.profiles.active}")
	private String profile;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ". Tipo: " + Pedido.class));

	}

	public Pedido insert(Pedido obj) {
		
		Cliente cliente = clienteService.find(obj.getCliente().getId());
		
		UserSS user = UserService.authenticated();
		if (user==null || !cliente.getId().equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		obj.setCliente(clienteService.find(obj.getCliente().getId()));

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

		if (profile.equalsIgnoreCase("test")) {
			mockEmailService.sendOrderConfirmationEmail(obj); /* Testando email com mockEmailService */
		} else if (profile.equalsIgnoreCase("dev") || profile.equalsIgnoreCase("prod" )) {
			//smtpEmailService.sendOrderConfirmationEmail(obj); /* testando email com smtp do Google sem formatação */
			 smtpEmailService.sendOrderConfirmationHtmlEmail(obj); /* testando email com
			// smtp do Google com formatação */
		}

		return obj;

	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
