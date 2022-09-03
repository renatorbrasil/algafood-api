package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.dto.model.GrupoModel;
import com.algaworks.algafood.api.mapper.GrupoMapper;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private GrupoMapper grupoMapper;

	@GetMapping
	public List<GrupoModel> listar() {
		List<Grupo> grupos = grupoRepository.findAll();
		return grupoMapper.map(grupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return  grupoMapper.map(cadastroGrupoService.buscar(grupoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoMapper.map(grupoInput);
		grupo = cadastroGrupoService.salvar(grupo);
		return grupoMapper.map(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId,
			@RequestBody GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupoService.buscar(grupoId);
		grupoMapper.copyDtoToDomain(grupoInput, grupoAtual);
		grupoAtual = cadastroGrupoService.salvar(grupoAtual);

		return grupoMapper.map(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupoService.excluir(grupoId);
	}
	
}
