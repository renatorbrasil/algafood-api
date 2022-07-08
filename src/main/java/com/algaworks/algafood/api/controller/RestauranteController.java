package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import com.algaworks.algafood.infrastructure.repository.spec.SpecsBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private SmartValidator validator;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {

		var specBuilder = new SpecsBuilder<Restaurante>();
		var specArray = new ArrayList<Specification<Restaurante>>();

		specArray.add(RestauranteSpecs.comFreteGratis());
		specArray.add(RestauranteSpecs.comNomeSemelhante(nome));

		return restauranteRepository.findAll(specBuilder.and(specArray));
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return cadastroRestaurante.buscar(restauranteId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(
			@RequestBody @Valid Restaurante restaurante) {
		try {
			return cadastroRestaurante.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar (
			@PathVariable Long restauranteId,
			@RequestBody @Valid Restaurante restaurante) {
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual,
				"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		try {
			return cadastroRestaurante.salvar(restauranteAtual);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
										@RequestBody Map<String, Object> campos,
										HttpServletRequest request) {
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

		merge(campos, restauranteAtual, request);
		validate(restauranteAtual);
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);

				if (field != null && !nomePropriedade.equals("id")) {
					field.setAccessible(true);
					Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
					ReflectionUtils.setField(field, restauranteDestino, novoValor);
				}
			});
		} catch (IllegalArgumentException e) {
			ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}

	}

	private void validate(Restaurante restaurante) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, "restaurante");
		validator.validate(restaurante, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

}
