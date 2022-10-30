package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoMapper extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoMapper() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = modelMapper.map(pedido, PedidoModel.class);
        pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoModel.add(AlgaLinks.linkToPedidos());

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            adicionarLinkMudancaStatus(pedido, pedidoModel);
        }

        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        /*
        * request = null não tem importância aqui, já que o 'methodOn' não chama o método realmente.
        * */
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

    private void adicionarLinkMudancaStatus(Pedido pedido, PedidoModel pedidoModel) {

        if (pedido.podeSerConfirmado()) {
            pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
                    .confirmar(pedido.getCodigo())).withRel("confirmar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
                    .cancelamento(pedido.getCodigo())).withRel("cancelar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
                    .entrega(pedido.getCodigo())).withRel("entregar"));
        }

    }

    public Pedido toDomain(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyDomainToObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
