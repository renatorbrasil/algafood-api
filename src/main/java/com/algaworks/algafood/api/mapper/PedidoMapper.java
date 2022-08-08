package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModel map(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> map(List<Pedido> pedidos) {
        return pedidos
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Pedido map(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyDomainToObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
