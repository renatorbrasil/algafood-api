package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.RestauranteInput;
import com.algaworks.algafood.api.dto.model.RestauranteModel;
import com.algaworks.algafood.api.mapper.RestauranteMapper;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import com.algaworks.algafood.infrastructure.repository.spec.SpecsBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

	@Autowired
	private RestauranteMapper restauranteMapper;

	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteMapper.map(restauranteRepository.findAll());
	}

	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> restaurantesComFreteGratis(String nome) {

		var specBuilder = new SpecsBuilder<Restaurante>();
		var specArray = new ArrayList<Specification<Restaurante>>();

		specArray.add(RestauranteSpecs.comFreteGratis());
		specArray.add(RestauranteSpecs.comNomeSemelhante(nome));

		return restauranteMapper.map(
				restauranteRepository.findAll(specBuilder.and(specArray))
		);
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		return restauranteMapper.map(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteMapper.map(restauranteInput);
			return restauranteMapper.map(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar (
			@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

		restauranteMapper.copyDtoToDomain(restauranteInput, restauranteAtual);

		try {
			return restauranteMapper.map(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.desativar(restauranteId);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestaurante.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestaurante.desativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}


	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
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
