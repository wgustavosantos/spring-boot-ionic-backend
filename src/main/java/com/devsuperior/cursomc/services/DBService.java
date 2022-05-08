package com.devsuperior.cursomc.services;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devsuperior.cursomc.domain.Categoria;
import com.devsuperior.cursomc.domain.Cidade;
import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.domain.Endereco;
import com.devsuperior.cursomc.domain.Estado;
import com.devsuperior.cursomc.domain.ItemPedido;
import com.devsuperior.cursomc.domain.Pagamento;
import com.devsuperior.cursomc.domain.PagamentoComBoleto;
import com.devsuperior.cursomc.domain.PagamentoComCartao;
import com.devsuperior.cursomc.domain.Pedido;
import com.devsuperior.cursomc.domain.Produto;
import com.devsuperior.cursomc.domain.enums.Estadopagamento;
import com.devsuperior.cursomc.domain.enums.Perfil;
import com.devsuperior.cursomc.domain.enums.TipoCliente;
import com.devsuperior.cursomc.repositories.CategoriaRepository;
import com.devsuperior.cursomc.repositories.CidadeRepository;
import com.devsuperior.cursomc.repositories.ClienteRepository;
import com.devsuperior.cursomc.repositories.EnderecoRepository;
import com.devsuperior.cursomc.repositories.EstadoRepository;
import com.devsuperior.cursomc.repositories.ItemPedidoRepository;
import com.devsuperior.cursomc.repositories.PagamentoRepository;
import com.devsuperior.cursomc.repositories.PedidoRepository;
import com.devsuperior.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BCryptPasswordEncoder bCPE;

	public void instantiateTestDatabase() {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2, p4));
		cat3.setProdutos(Arrays.asList(p4, p6));
		cat4.setProdutos(Arrays.asList(p1, p2, p3, p7));
		cat5.setProdutos(Arrays.asList(p8));
		cat6.setProdutos(Arrays.asList(p9, p10));
		cat7.setProdutos(Arrays.asList(p11));

		p1.setCategorias(Arrays.asList(cat1, cat4));
		p2.setCategorias(Arrays.asList(cat1, cat2, cat4));
		p3.setCategorias(Arrays.asList(cat1, cat4));
		p4.setCategorias(Arrays.asList(cat2));
		p5.setCategorias(Arrays.asList(cat3));
		p6.setCategorias(Arrays.asList(cat3));
		p7.setCategorias(Arrays.asList(cat4));
		p8.setCategorias(Arrays.asList(cat5));
		p9.setCategorias(Arrays.asList(cat6));
		p10.setCategorias(Arrays.asList(cat6));
		p11.setCategorias(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado("São Paulo");
		Estado est2 = new Estado("Minas Gerais");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c2, c3));
		est2.getCidades().addAll(Arrays.asList(c1));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "gustavoinf18@gmail.com", "363789", TipoCliente.PESSOAFISICA,
				bCPE.encode("12345678"));
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		

		Cliente cli2 = new Cliente(null, "Gustavo Santos", "guto15santos@gmail.com", "64195478499", TipoCliente.PESSOAFISICA, bCPE.encode("12345678"));
		cli2.getTelefones().addAll(Arrays.asList("91993720104", "984321251"));
		cli2.addPerfil(Perfil.ADMIN);

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", c1, cli1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		Endereco e3 = new Endereco(null, "General Gurjão", "1486", "Prox ao Clube Paroquial", "Pe Luiz", "68600000", c2, cli2);

		cli1.setEnderecos(Arrays.asList(e1, e2));
		cli2.setEnderecos(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		Pedido ped1 = new Pedido(null, LocalDateTime.of(2022, 02, 04, 10, 32), e1, cli1);
		Pedido ped2 = new Pedido(null, LocalDateTime.of(2022, 02, 10, 19, 35), e2, cli1);

		Pagamento pagto1 = new PagamentoComCartao(null, Estadopagamento.QUITADO, ped1, 6);

		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, Estadopagamento.PENDENTE, ped2,
				LocalDateTime.of(2022, 02, 11, 00, 00), null);

		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);

		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);

		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip2));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

		// ped1.setItens(Set.of(ip1, ip2));

	}

}
