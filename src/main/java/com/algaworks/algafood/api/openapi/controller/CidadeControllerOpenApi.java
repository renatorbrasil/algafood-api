package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.dto.model.CidadeModel;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Cidades", description = "Gerencia cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Listar todas as cidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    List<CidadeModel> listar();

    @Operation(summary = "Buscar cidade por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Cidade não encontrada", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(description = "Id da cidade inválido", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    CidadeModel buscar(@Parameter(description = "ID de uma cidade") Long cidadeId);

    @Operation(summary = "Cadastrar uma nova cidade")
    CidadeModel adicionar(CidadeInput cidadeInput);

    @Operation(summary = "Atualizar uma cidade por ID")
    CidadeModel atualizar(@Parameter(description = "ID de uma cidade") Long cidadeId,
                                 CidadeInput cidadeInput);

    @Operation(summary = "Excluir uma cidade por ID")
    void remover(@Parameter(description = "ID de uma cidade") Long cidadeId);

}
