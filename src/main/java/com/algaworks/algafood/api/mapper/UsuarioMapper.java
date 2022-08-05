package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.UsuarioInput;
import com.algaworks.algafood.api.dto.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel map(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> map(Collection<Usuario> usuarios) {
        return usuarios
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Usuario map(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyDtoToDomain(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}
