package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.SenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioInput;
import com.algaworks.algafood.api.dto.model.UsuarioModel;
import com.algaworks.algafood.api.mapper.UsuarioMapper;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioMapper.toCollectionModel(usuarios);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return  usuarioMapper.toModel(cadastroUsuario.buscar(usuarioId));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioMapper.toDomain(usuarioInput);
		usuario = cadastroUsuario.salvar(usuario);
		return usuarioMapper.toModel(usuario);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuario.buscar(usuarioId);
		usuarioMapper.copyDtoToDomain(usuarioInput, usuarioAtual);
		usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

		return usuarioMapper.toModel(usuarioAtual);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId,
								  @RequestBody @Valid SenhaInput senhaInput) {
		cadastroUsuario.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}
	
}
