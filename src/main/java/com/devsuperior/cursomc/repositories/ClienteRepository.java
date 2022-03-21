package com.devsuperior.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	/* Adicionar o prefixo findBy + o nome do atributo da entidade que quer buscar no banco de dados */
	/* O parâmetro “readOnly” na anotação @Transactional epecifica que 
	 * nenhuma operação de DML poderá ser executada (Insert, Delete ou Update), ou seja, apenas consultas.
	 */
	@Transactional(readOnly = true) /* org.springframework.transaction.annotation.Transactional */
	Cliente findByEmail(String email);

}
