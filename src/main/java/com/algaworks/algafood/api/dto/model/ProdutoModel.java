package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoModel {
    private Long id;
    private String nome;
    private String descricao;
    private Long preco;
    private Boolean ativo;
}
