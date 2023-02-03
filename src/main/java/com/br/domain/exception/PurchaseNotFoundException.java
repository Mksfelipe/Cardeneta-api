package com.br.domain.exception;

public class PurchaseNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PurchaseNotFoundException(String mensagem) {
			super(mensagem);
		}

	public PurchaseNotFoundException(Long id) {
			this(String.format("Não existe um cadastro de compra com código %d", id));
	}

}
