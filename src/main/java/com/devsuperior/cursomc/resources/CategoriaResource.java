package com.devsuperior.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.cursomc.domain.Categoria;
import com.devsuperior.cursomc.domain.dtos.CategoriaDTO;
import com.devsuperior.cursomc.services.CategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value = "/{id}", method =RequestMethod.GET)
	@ApiOperation(value = "Busca uma categoria por id")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {	
		
		Categoria categoria = categoriaService.find(id);
		
		/*return ResponseEntity.ok(categoria);*/
		return ResponseEntity.ok().body(categoria);
		
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Insere uma categoria")
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		/* setando URI na resposta*/
		
		Categoria obj = categoriaService.fromDTO(objDTO);
		obj = categoriaService.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Atualiza uma categoria passando um id no parâmetro da requisição")
	public ResponseEntity<Void> udpdate(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDTO ){

		Categoria obj = categoriaService.fromDTO(objDTO);
		
		obj.setId(id);
		obj = categoriaService.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Deleta uma categoria por id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente") })
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		categoriaService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@ApiOperation(value = "Retorna todas as categorias cadastradas")
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> listObj = categoriaService.findAll();
		List<CategoriaDTO> listObjDTO = 
				listObj.stream().map
				(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listObjDTO);
	}
	
	@GetMapping(value = "/page")
	@ApiOperation(value = "Retorna categorias por paginação")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,/*24 pois é multiplo de 1, 2, 3 e 4 para criar layouts sem quebrar */
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
		Page<Categoria> listObj = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listObjDTO = 
				listObj.map
				(obj -> new CategoriaDTO(obj));			
	
		return ResponseEntity.ok().body(listObjDTO);
	}

}
