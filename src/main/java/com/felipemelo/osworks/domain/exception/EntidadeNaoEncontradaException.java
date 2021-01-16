package com.felipemelo.osworks.domain.exception;

public class EntidadeNaoEncontradaException extends DomainException{
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
