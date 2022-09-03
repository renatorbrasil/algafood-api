package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.input.RestauranteInput;
import com.algaworks.algafood.api.dto.model.RestauranteModel;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoOpenApiModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista restaurantes",
            parameters = {@Parameter(in = ParameterIn.QUERY,
                    name = "projecao",
                    content = @Content(schema = @Schema(type = "string")),
                    example = "nomes")},
            responses = @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteBasicoOpenApiModel.class)))
    )
    List<RestauranteModel> listar();

    @Operation(hidden = true)
    List<RestauranteModel> apenasNomes();

    List<RestauranteModel> restaurantesComFreteGratis(String nome);

    RestauranteModel buscar(Long restauranteId);

    RestauranteModel adicionar(RestauranteInput restauranteInput);

    RestauranteModel atualizar (Long restauranteId, RestauranteInput restauranteInput);

    void ativar(Long restauranteId);

    void desativar(Long restauranteId);

    void ativarMultiplos(List<Long> restaurantesIds);

    void inativarMultiplos(List<Long> restaurantesIds);

    void abrir(Long restauranteId);

    void fechar(Long restauranteId);


}
