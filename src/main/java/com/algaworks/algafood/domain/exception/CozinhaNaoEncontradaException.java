package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        super(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
    }
}