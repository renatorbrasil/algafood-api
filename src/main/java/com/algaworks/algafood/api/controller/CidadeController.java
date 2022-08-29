package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.dto.model.CidadeModel;
import com.algaworks.algafood.api.mapper.CidadeMapper;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeMapper cidadeMapper;

	@ApiOperation("Listar todas as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeMapper.map(cidadeRepository.findAll());
	}

	@ApiOperation("Buscar cidade por id")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable @ApiParam(value = "ID de uma cidade") Long cidadeId) {
		return cidadeMapper.map(cadastroCidade.buscar(cidadeId));
	}

	@ApiOperation("Cadastrar uma nova cidade")
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

	@ApiOperation("Atualizar uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable @ApiParam("ID de uma cidade") Long cidadeId,
							@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);
			cidadeMapper.copyDtoToDomain(cidadeInput, cidadeAtual);
			return cidadeMapper.map(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@ApiOperation("Excluir uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable @ApiParam("ID de uma cidade") Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}


}
