package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.dto.input.UsuarioInput;
import com.algaworks.algafood.api.dto.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioMapper extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioMapper() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = modelMapper.map(usuario, UsuarioModel.class);

        usuarioModel.add(linkTo(methodOn(UsuarioController.class).buscar(usuarioModel.getId()))
                .withSelfRel());

        usuarioModel.add(linkTo(methodOn(UsuarioController.class).listar())
                .withRel(IanaLinkRelations.COLLECTION));

        usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioModel.getId()))
                .withRel("grupos-usuario"));

        return usuarioModel;

    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }

    public Usuario toDomain(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyDtoToDomain(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}
