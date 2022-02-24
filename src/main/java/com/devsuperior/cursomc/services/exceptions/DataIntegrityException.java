package com.devsuperior.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String MsgErro) {
		super(MsgErro);
	}
	
	public DataIntegrityException(String MsgErro, Throwable causa) {
		super(MsgErro, causa);
	}

}
