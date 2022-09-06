package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoMapper
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoMapper() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = modelMapper.map(pedido, PedidoResumoModel.class);

        pedidoResumoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoResumoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        pedidoResumoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoResumoModel;
    }

    public List<PedidoResumoModel> toDomain(List<Pedido> pedidos) {
        return pedidos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
