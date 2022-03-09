package com.devsuperior.cursomc;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

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

		cat1.setProdutos(Arrays.asList(p1, p2, p3));

		cat2.setProdutos(Arrays.asList(p2));

		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1, cat2));
		p3.setCategorias(Arrays.asList(cat1));

		Estado est1 = new Estado("São Paulo");
		Estado est2 = new Estado("Minas Gerais");

		Cidade c1 = new Cidade("Uberlândia", est1);
		Cidade c2 = new Cidade("São Paulo", est2);
		Cidade c3 = new Cidade("Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c2, c3));
		est2.getCidades().addAll(Arrays.asList(c1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "363789", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", c1, cli1);
		Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);

		cli1.setEnderecos(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		Pedido ped1 = new Pedido(null, LocalDateTime.of(2022, 02, 04, 10, 32), e1, cli1);
		Pedido ped2 = new Pedido(null, LocalDateTime.of(2022, 02, 10, 19, 35), e2, cli1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, Estadopagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, Estadopagamento.PENDENTE, ped2, LocalDateTime.of(2022, 02, 11, 00, 00), null);
		
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
		
		//ped1.setItens(Set.of(ip1, ip2));
		
	}

}