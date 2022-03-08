package com.devsuperior.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.domain.dtos.ClienteDTO;
import com.devsuperior.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/{id}", method =RequestMethod.GET)
	public ResponseEntity<Cliente> listar(@PathVariable Integer id) {	
		
		Cliente cliente = clienteService.find(id);
		
		/*return ResponseEntity.ok(cliente);*/
		return ResponseEntity.ok().body(cliente);
		
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> udpdate(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO ){

		Cliente obj = clienteService.fromDTO(objDTO);
		
		obj.setId(id);
		obj = clienteService.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> listObj = clienteService.findAll();
		List<ClienteDTO> listObjDTO = 
				listObj.stream().map
				(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listObjDTO);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,/*24 pois é multiplo de 1, 2, 3 e 4 para criar layouts sem quebrar */
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
		Page<Cliente> listObj = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listObjDTO = 
				listObj.map
				(obj -> new ClienteDTO(obj));			
	
		return ResponseEntity.ok().body(listObjDTO);
	}

}
