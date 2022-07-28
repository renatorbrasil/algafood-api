package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.dto.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel map(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> map(List<Grupo> grupos) {
        return grupos
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Grupo map(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyDtoToDomain(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
