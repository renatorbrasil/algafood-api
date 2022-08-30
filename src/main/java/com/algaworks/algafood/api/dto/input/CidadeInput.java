package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInput {

    @ApiModelProperty(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(required = true)
    @Valid
    @NotNull
    private EstadoIdInput estado;
}
