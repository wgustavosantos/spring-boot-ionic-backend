package com.devsuperior.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.devsuperior.cursomc.domain.Cidade;
import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.domain.Endereco;
import com.devsuperior.cursomc.domain.dtos.ClienteDTO;
import com.devsuperior.cursomc.domain.dtos.ClienteNewDTO;
import com.devsuperior.cursomc.domain.enums.TipoCliente;
import com.devsuperior.cursomc.repositories.ClienteRepository;
import com.devsuperior.cursomc.repositories.EnderecoRepository;
import com.devsuperior.cursomc.services.exceptions.DataIntegrityException;
import com.devsuperior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ". Tipo: " + Cliente.class));
	
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {

		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) {

		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar porque há entidades relacionadas");
		}

	}

	public List<Cliente> findAll() {
		return repository.findAll();

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clieDTO) {

		return new Cliente(clieDTO.getId(), clieDTO.getNome(), clieDTO.getEmail(), null, null);

	}
	
	public Cliente fromDTO(ClienteNewDTO clieDTO) {

		Cliente cliente = new Cliente(null, clieDTO.getNome(), clieDTO.getEmail(), clieDTO.getCpfOuCnpj(), TipoCliente.toEnum(clieDTO.getTipo()));
		Cidade cidade = new Cidade(clieDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clieDTO.getLogradouro(), clieDTO.getNumero(), clieDTO.getComplemento(), clieDTO.getBairro(), clieDTO.getCep(), cidade, cliente);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clieDTO.getTelefone1());
		
			if(clieDTO.getTelefone2() != null) {
				cliente.getTelefones().add(clieDTO.getTelefone2());
				
			}
			if(clieDTO.getTelefone3() != null) {
				cliente.getTelefones().add(clieDTO.getTelefone3());
				
			}
			return cliente;
			
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}


}