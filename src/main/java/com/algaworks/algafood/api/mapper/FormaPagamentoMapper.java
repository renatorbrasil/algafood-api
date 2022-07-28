package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.FormaPagamentoInput;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel map(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> map(Collection<FormaPagamento> formasPagamento) {
        return formasPagamento
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public FormaPagamento map(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyDtoToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}
