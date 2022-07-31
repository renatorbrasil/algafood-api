package com.algaworks.algafood.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInput {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoInput estado;
}