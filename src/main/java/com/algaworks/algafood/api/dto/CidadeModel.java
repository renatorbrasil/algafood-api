package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeModel {
    private Long id;
    private String nome;
    private EstadoModel estado;
}
