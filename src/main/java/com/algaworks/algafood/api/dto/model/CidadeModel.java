package com.algaworks.algafood.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Setter
@Getter
public class CidadeModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Belo Horizonte")
    private String nome;

    private EstadoModel estado;
}
