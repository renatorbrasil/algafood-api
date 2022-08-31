package com.algaworks.algafood.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Minas Gerais")
    private String nome;
}
