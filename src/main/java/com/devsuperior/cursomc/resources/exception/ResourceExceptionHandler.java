package com.devsuperior.cursomc.resources.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException 
	(ObjectNotFoundException e, HttpServletRequest status){
		
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value()
				, e.getMessage()
				, LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}

}
