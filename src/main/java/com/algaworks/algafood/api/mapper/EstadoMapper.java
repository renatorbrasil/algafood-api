package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.api.dto.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel domainToDto(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> domainToDto(List<Estado> estados) {
        return estados
                .stream()
                .map(this::domainToDto)
                .collect(Collectors.toList());
    }

    public Estado dtoToDomain(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyDtoToDomain(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
