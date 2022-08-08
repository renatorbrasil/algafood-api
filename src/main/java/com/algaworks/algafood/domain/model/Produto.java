package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String descricao;

	@Column(nullable = false)
	private BigDecimal preco;

	@Column
	private Boolean ativo = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

}