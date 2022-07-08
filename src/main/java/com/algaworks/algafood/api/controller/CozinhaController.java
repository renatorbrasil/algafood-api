package com.algaworks.algafood.api.controller;

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

	@GetMapping
	public List<Cozinha> listar(String nome) {
		if (StringUtils.hasText(nome)) {
			return cozinhaRepository.findAllByNomeContaining(nome);
		}
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		return  cadastroCozinha.buscar(cozinhaId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cadastroCozinha.salvar(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	
}
