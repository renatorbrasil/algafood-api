package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel map(Permissao grupo) {
        return modelMapper.map(grupo, PermissaoModel.class);
    }

    public List<PermissaoModel> map(Collection<Permissao> grupos) {
        return grupos
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
