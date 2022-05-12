package com.devsuperior.cursomc.services.exceptions;

public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String MsgErro) {
		super(MsgErro);
	}
	
	public AuthorizationException(String MsgErro, Throwable causa) {
		super(MsgErro, causa);
	}

}
