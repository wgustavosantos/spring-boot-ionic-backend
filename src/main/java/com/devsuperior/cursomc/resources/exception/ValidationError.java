package com.devsuperior.cursomc.resources.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError(Integer status, String msg, LocalDateTime localDate) {
		super(status, msg, localDate);
		// Construtor da supper classe
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String message) {

		erros.add(new FieldMessage(fieldName, message));

	}

}
