package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.model.GrupoModel;
import com.algaworks.algafood.api.mapper.GrupoMapper;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {


	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private GrupoMapper grupoMapper;

	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long usuarioId) {
		return grupoMapper.map(cadastroUsuario.buscarGrupos(usuarioId));
	}

	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
	}



}
