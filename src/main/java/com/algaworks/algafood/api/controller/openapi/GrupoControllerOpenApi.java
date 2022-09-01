package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.dto.model.GrupoModel;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Grupos", description = "Gerencia os grupos de usuários")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Listar todos os grupos")
    List<GrupoModel> listar();

    @Operation(summary = "Listar grupo por ID")
    @ApiResponses(value = {
            @ApiResponse(description = "Grupo encontrado", responseCode = "200"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(description = "Grupo não encontrado", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    GrupoModel buscar(@Parameter(description = "ID de um grupo") Long grupoId);


    @Operation(summary = "Adicionar um novo grupo")
    @ApiResponses(value = {
            @ApiResponse(description = "Grupo adicionado", responseCode = "201"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    GrupoModel adicionar(GrupoInput grupoInput);


    @Operation(summary = "Atualizar grupo por ID")
    @ApiResponses(value = {
            @ApiResponse(description = "Grupo atualizado", responseCode = "200"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(description = "Grupo não encontrado", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    GrupoModel atualizar(@Parameter(description = "ID de um grupo") Long grupoId, GrupoInput grupoInput);


    @Operation(summary = "Excluir grupo por ID")
    @ApiResponses(value = {
            @ApiResponse(description = "Grupo excluído", responseCode = "204"),
            @ApiResponse(description = "Requisição inválida (erro do cliente)", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(description = "Grupo não encontrado", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(description = "Grupo está em uso e não pode ser excluído", responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    void remover(@Parameter(description = "ID de um grupo") Long grupoId);

}
