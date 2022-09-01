package com.algaworks.algafood.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Gerente")
    private String nome;
}
