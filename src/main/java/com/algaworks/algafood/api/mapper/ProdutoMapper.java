package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.ProdutoInput;
import com.algaworks.algafood.api.dto.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel map(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> map(Collection<Produto> produtos) {
        return produtos
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Produto map(ProdutoInput produtoI) {
        return modelMapper.map(produtoI, Produto.class);
    }

    public void copyDtoToDomain(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
