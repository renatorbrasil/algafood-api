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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Cidades", description = "Gerencia cidades")
@SecurityRequirement(name = "security_auth")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Listar todas as cidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
    })
    CollectionModel<CidadeModel> listar();

    @Operation(summary = "Buscar cidade por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Cidade não encontrada", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema"))),
            @ApiResponse(description = "Id da cidade inválido", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(ref = "Problema")))
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
