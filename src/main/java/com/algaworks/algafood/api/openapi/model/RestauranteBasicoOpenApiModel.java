package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.model.CozinhaModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(name = "RestauranteBasicoModel")
@Getter
public class RestauranteBasicoOpenApiModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

    @Schema(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;
}
