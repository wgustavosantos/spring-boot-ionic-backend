package com.devsuperior.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.cursomc.domain.Categoria;
import com.devsuperior.cursomc.domain.Produto;
import com.devsuperior.cursomc.domain.dtos.CategoriaDTO;
import com.devsuperior.cursomc.domain.dtos.ProdutoDTO;
import com.devsuperior.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method =RequestMethod.GET)
	public ResponseEntity<Produto> listar(@PathVariable Integer id) {	
		
		Produto produto = service.find(id);
		
		/*return ResponseEntity.ok(categoria);*/
		return ResponseEntity.ok().body(produto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,/*24 pois é multiplo de 1, 2, 3 e 4 para criar layouts sem quebrar */
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
		Page<Categoria> listObj = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listObjDTO = 
				listObj.map
				(obj -> new CategoriaDTO(obj));			
	
		return ResponseEntity.ok().body(listObjDTO);
	}

}
