package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import com.algaworks.algafood.api.dto.model.FotoProdutoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = "Restaurante")
public interface RestauranteProdutoFotoControllerOpenApi {

    FotoProdutoModel atualizarFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput)
            throws IOException;

    void apagarFoto(Long restauranteId, Long produtoId);

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)),
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE))
    })
    FotoProdutoModel buscar(Long restauranteId, Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

}
