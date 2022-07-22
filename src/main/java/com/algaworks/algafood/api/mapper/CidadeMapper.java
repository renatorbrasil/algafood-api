package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.*;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModel domainToDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> domainToDto(List<Cidade> cidades) {
        return cidades
                .stream()
                .map(this::domainToDto)
                .collect(Collectors.toList());
    }

    public Cidade dtoToDomain(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyDtoToDomain(CidadeInput cidadeInput, Cidade cidade) {
        /*
         * Essa próxima linha é para evitar a exception:
         * org.hibernate.HibernateException: indentifier of an instance of
         * com.algaworks.algafood.domain.model.Estado was altered from 1 to 2.
         */
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }
}
