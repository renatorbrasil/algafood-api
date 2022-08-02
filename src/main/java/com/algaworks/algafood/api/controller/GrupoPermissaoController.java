package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.model.PermissaoModel;
import com.algaworks.algafood.api.mapper.PermissaoMapper;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {


	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private PermissaoMapper permissaoMapper;

	@GetMapping
	public List<PermissaoModel> listar(@PathVariable("grupoId") Long grupoId) {
		Set<Permissao> permissoes = cadastroGrupo.buscar(grupoId).getPermissoes();
		return permissaoMapper.map(permissoes);
	}

	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionar(@PathVariable("grupoId") Long grupoId,
						  @PathVariable("permissaoId") Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("grupoId") Long grupoId,
						@PathVariable("permissaoId") Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}

	
}
