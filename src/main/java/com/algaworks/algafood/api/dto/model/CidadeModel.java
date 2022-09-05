package com.algaworks.algafood.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Belo Horizonte")
    private String nome;

    private EstadoModel estado;
}
