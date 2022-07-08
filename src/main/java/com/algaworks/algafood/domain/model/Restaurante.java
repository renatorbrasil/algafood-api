package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ValorZeroIncluiDescricao(
		valorField = "taxaFrete",
		descricaoField = "nome",
		descricaoObrigatoria = "Frete Grátis"
)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotNull
	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@Valid
	@ConvertGroup(to = Groups.CozinhaId.class)
	@NotNull
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Cozinha cozinha;

	@JsonIgnore
	@Embedded
	private Endereco endereco;

	@JsonIgnore
	@org.hibernate.annotations.CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@org.hibernate.annotations.UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
	)
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
}
