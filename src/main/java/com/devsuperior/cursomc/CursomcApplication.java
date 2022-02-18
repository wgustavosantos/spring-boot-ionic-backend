package com.devsuperior.cursomc;

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
import com.devsuperior.cursomc.domain.Produto;
import com.devsuperior.cursomc.domain.enums.TipoCliente;
import com.devsuperior.cursomc.repositories.CategoriaRepository;
import com.devsuperior.cursomc.repositories.CidadeRepository;
import com.devsuperior.cursomc.repositories.ClienteRepository;
import com.devsuperior.cursomc.repositories.EnderecoRepository;
import com.devsuperior.cursomc.repositories.EstadoRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

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

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente("Maria Silva", "maria@gmail.com", "363789", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", c1, cli1);
		Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);

		cli1.setEnderecos(Arrays.asList(e1, e2));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

	}

}
