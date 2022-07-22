package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoInput {

    @NotNull
    private String nome;
}
