package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.FormaPagamentoInput;
import com.algaworks.algafood.api.dto.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel domainToDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> domainToDto(List<FormaPagamento> formasPagamento) {
        return formasPagamento
                .stream()
                .map(this::domainToDto)
                .collect(Collectors.toList());
    }

    public FormaPagamento dtoToDomain(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyDtoToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}
