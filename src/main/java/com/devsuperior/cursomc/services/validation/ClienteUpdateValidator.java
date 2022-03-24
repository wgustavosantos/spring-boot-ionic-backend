package com.devsuperior.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.devsuperior.cursomc.domain.Cliente;
import com.devsuperior.cursomc.domain.dtos.ClienteDTO;
import com.devsuperior.cursomc.repositories.ClienteRepository;
import com.devsuperior.cursomc.resources.exception.FieldMessage;

//nome do validator
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { // 	name of the annotation,
																								// for which class it is intended
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired																						
	ClienteRepository repo;
	@Override
	public void initialize(ClienteUpdate ann) {// name of the annotation
	}

	@Override // if the list does not contain errors, it will return a boolean for empty list
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); // p atualizar email
		
		Integer uriId = Integer.parseInt(map.get("id")); // p atualizar email
		
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) { // se aux for null - n existe email, se equals(uriId) for true, o id de quem quer atualizar email é o mesmo
			list.add(new FieldMessage("Email", "Email já cadastrado"));
		}
		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}