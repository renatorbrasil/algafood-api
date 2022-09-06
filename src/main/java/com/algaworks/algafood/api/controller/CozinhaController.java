package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.CozinhaInput;
import com.algaworks.algafood.api.dto.model.CozinhaModel;
import com.algaworks.algafood.api.mapper.CozinhaMapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaMapper cozinhaMapper;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping
	@PageableAsQueryParam
	public PagedModel<CozinhaModel> listar(@Parameter(hidden = true) @PageableDefault(size = 5) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		return pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaMapper);
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return  cozinhaMapper.toModel(cadastroCozinha.buscar(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaMapper.toDomain(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);
		return cozinhaMapper.toModel(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
			@RequestBody CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);
		cozinhaMapper.copyDtoToDomain(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

		return cozinhaMapper.toModel(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	
}
