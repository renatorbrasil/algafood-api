package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoMapper extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoMapper() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = modelMapper.map(pedido, PedidoModel.class);
        pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoModel.add(linkTo(PedidoController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoModel.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });

        return pedidoModel;
    }

    public Pedido toDomain(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyDomainToObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
