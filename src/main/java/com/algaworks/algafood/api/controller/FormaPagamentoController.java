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
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		long eTag = 0;

		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

		if (dataUltimaAtualizacao != null) {
			eTag = dataUltimaAtualizacao.toEpochSecond();
		}

		if (request.checkNotModified(String.valueOf(eTag))) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
					.eTag(String.valueOf(eTag))
					.build();
		}

		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		List<FormaPagamentoModel> formasPagamentoModel = formaPagamentoMapper.map(formasPagamento);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(String.valueOf(eTag))
				.body(formasPagamentoModel);
	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		long eTag = 0;

		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao(formaPagamentoId);

		if (dataUltimaAtualizacao != null) {
			eTag = dataUltimaAtualizacao.toEpochSecond();
		}

		if (request.checkNotModified(String.valueOf(eTag))) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
					.eTag(String.valueOf(eTag))
					.build();
		}

		var formaPagamento =
				formaPagamentoMapper.map(cadastroFormaPagamento.buscar(formaPagamentoId));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoMapper.map(formaPagamentoInput);
			return formaPagamentoMapper.map(cadastroFormaPagamento.salvar(formaPagamento));
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
			return formaPagamentoMapper.map(cadastroFormaPagamento.salvar(formaPagamentoAtual));
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
