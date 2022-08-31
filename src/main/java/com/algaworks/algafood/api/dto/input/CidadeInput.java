package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInput {

    @Schema(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;

    @Schema(required = true)
    @Valid
    @NotNull
    private EstadoIdInput estado;
}
