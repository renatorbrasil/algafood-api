package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.model.UsuarioModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Restaurante")
public interface RestauranteResponsavelControllerOpenApi {

    CollectionModel<UsuarioModel> listar(Long restauranteId);

    void desassociar(Long restauranteId, Long usuarioId);

    void associar(Long restauranteId, Long usuarioId);
}
