package com.devsuperior.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String MsgErro) {
		super(MsgErro);
	}
	
	public ObjectNotFoundException(String MsgErro, Throwable causa) {
		super(MsgErro, causa);
	}

}
