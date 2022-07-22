package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.CozinhaInput;
import com.algaworks.algafood.api.dto.CozinhaModel;
import com.algaworks.algafood.api.mapper.CozinhaMapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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

	@GetMapping
	public List<CozinhaModel> listar(String nome) {
		List<Cozinha> cozinhas = null;
		if (StringUtils.hasText(nome)) {
			cozinhas = cozinhaRepository.findAllByNomeContaining(nome);
		} else {
			cozinhas = cozinhaRepository.findAll();
		}
		return cozinhaMapper.domainToDto(cozinhas);
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return  cozinhaMapper.domainToDto(cadastroCozinha.buscar(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaMapper.dtoToDomain(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);
		return cozinhaMapper.domainToDto(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
			@RequestBody CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);
		cozinhaMapper.copyDtoToDomain(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

		return cozinhaMapper.domainToDto(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	
}
