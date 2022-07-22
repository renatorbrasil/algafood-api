package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.EstadoInput;
import com.algaworks.algafood.api.dto.EstadoModel;
import com.algaworks.algafood.api.mapper.EstadoMapper;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoMapper estadoMapper;
	
	@GetMapping
	public List<EstadoModel> listar() {
		return estadoMapper.domainToDto(estadoRepository.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return estadoMapper.domainToDto(cadastroEstado.buscar(estadoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoMapper.dtoToDomain(estadoInput);
		return estadoMapper.domainToDto(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId,
							@RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscar(estadoId);
		estadoMapper.copyDtoToDomain(estadoInput, estadoAtual);
		estadoAtual = cadastroEstado.salvar(estadoAtual);

		return estadoMapper.domainToDto(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}
	
}
