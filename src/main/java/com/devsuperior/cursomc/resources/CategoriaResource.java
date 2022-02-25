package com.devsuperior.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.cursomc.domain.Categoria;
import com.devsuperior.cursomc.domain.dtos.CategoriaDTO;
import com.devsuperior.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value = "/{id}", method =RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {	
		
		Categoria categoria = categoriaService.find(id);
		
		/*return ResponseEntity.ok(categoria);*/
		return ResponseEntity.ok().body(categoria);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		/* setando URI na resposta*/
		obj = categoriaService.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> udpdate(@PathVariable Integer id, @RequestBody Categoria obj ){
		
		obj.setId(id);
		obj = categoriaService.update(obj);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		
		categoriaService.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> listObj = categoriaService.findAll();
		
		List<CategoriaDTO> listObjDTO = new ArrayList<>();
		
		for (Categoria categoria : listObj) {
			listObjDTO.add(new CategoriaDTO(categoria));
		}
		
		return ResponseEntity.ok().body(listObjDTO);
	}

}
