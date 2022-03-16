package com.devsuperior.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.devsuperior.cursomc.domain.dtos.ClienteNewDTO;
import com.devsuperior.cursomc.domain.enums.TipoCliente;
import com.devsuperior.cursomc.resources.exception.FieldMessage;
import com.devsuperior.cursomc.services.validation.utils.BR;

//nome do validator
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { // nome da anotação,
																									// para qual classe
																									// se destina
	@Override
	public void initialize(ClienteInsert ann) {// nome da anotação também
	}

	@Override // método que verifica se regra é válida ou não
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}