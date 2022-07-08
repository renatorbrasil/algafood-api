package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(Long cidadeId) {
        super(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
    }
}
