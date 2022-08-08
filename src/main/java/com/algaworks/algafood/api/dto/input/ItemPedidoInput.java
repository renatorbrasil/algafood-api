package com.algaworks.algafood.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @NotNull
    private Integer quantidade;

    private String observacao;

}
