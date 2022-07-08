package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
	public void deveTerSucessoAoCadastrarCozinha_QuandoComNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        Assertions.assertThat(novaCozinha).isNotNull();
        Assertions.assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveFalharAoCadastrarCozinha_QuandoSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        novaCozinha = cadastroCozinha.salvar(novaCozinha);
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalharAoExcluirCozinhaEmUso() {
        cadastroCozinha.excluir(1L);
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void deveFalharAoExcluiCozinhaInexistente() {
        cadastroCozinha.excluir(100L);
    }

}
