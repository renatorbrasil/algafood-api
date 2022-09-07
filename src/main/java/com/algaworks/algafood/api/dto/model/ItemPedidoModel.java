package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    private Long id;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;
    private Long produtoId;
    private String produtoNome;
}
