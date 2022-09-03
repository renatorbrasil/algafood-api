package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.model.UsuarioModel;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Restaurante")
public interface RestauranteResponsavelControllerOpenApi {

    List<UsuarioModel> listar(Long restauranteId);

    void desassociar(Long restauranteId, Long usuarioId);

    void associar(Long restauranteId, Long usuarioId);
}
