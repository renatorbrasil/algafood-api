package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formasPagamento")
@Setter
@Getter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {
    private Long id;
    private String descricao;
}
