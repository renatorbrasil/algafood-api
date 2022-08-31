package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.dto.model.CidadeModel;
import com.algaworks.algafood.api.mapper.CidadeMapper;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeMapper cidadeMapper;

	@Operation(summary = "Listar todas as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeMapper.map(cidadeRepository.findAll());
	}

	@Operation(summary = "Buscar cidade por id")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable @Parameter(description = "ID de uma cidade") Long cidadeId) {
		return cidadeMapper.map(cadastroCidade.buscar(cidadeId));
	}

	@Operation(summary = "Cadastrar uma nova cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeMapper.map(cidadeInput);
			return cidadeMapper.map(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Atualizar uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable @Parameter(description = "ID de uma cidade") Long cidadeId,
							@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);
			cidadeMapper.copyDtoToDomain(cidadeInput, cidadeAtual);
			return cidadeMapper.map(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Excluir uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable @Parameter(description = "ID de uma cidade") Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}


}
