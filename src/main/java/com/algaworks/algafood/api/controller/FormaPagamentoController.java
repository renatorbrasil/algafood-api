package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.FormaPagamentoInput;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModel;
import com.algaworks.algafood.api.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoMapper formaPagamentoMapper;
	
	@GetMapping
	public List<FormaPagamentoModel> listar() {
		return formaPagamentoMapper.domainToDto(formaPagamentoRepository.findAll());
	}

	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
		return formaPagamentoMapper.domainToDto(cadastroFormaPagamento.buscar(formaPagamentoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoMapper.dtoToDomain(formaPagamentoInput);
			return formaPagamentoMapper.domainToDto(cadastroFormaPagamento.salvar(formaPagamento));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
							@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscar(formaPagamentoId);
			formaPagamentoMapper.copyDtoToDomain(formaPagamentoInput, formaPagamentoAtual);
			return formaPagamentoMapper.domainToDto(cadastroFormaPagamento.salvar(formaPagamentoAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);
	}


}
