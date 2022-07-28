package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.CozinhaInput;
import com.algaworks.algafood.api.dto.model.CozinhaModel;
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

    public CozinhaModel map(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> map(List<Cozinha> cozinhas) {
        return cozinhas
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Cozinha map(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyDtoToDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
