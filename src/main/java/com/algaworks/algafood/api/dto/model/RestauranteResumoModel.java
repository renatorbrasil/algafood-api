package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {
    private Long id;
    private String nome;
}
