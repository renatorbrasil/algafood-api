package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.CidadeInput;
import com.algaworks.algafood.api.dto.CozinhaInput;
import com.algaworks.algafood.api.dto.CozinhaModel;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModel domainToDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> domainToDto(List<Cozinha> cozinhas) {
        return cozinhas
                .stream()
                .map(this::domainToDto)
                .collect(Collectors.toList());
    }

    public Cozinha dtoToDomain(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyDtoToDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
