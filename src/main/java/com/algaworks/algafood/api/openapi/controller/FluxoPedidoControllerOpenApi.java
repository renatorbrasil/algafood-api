package com.algaworks.algafood.api.openapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedido")
public interface FluxoPedidoControllerOpenApi {

    void confirmar(String codigoPedido);

    void entrega(String codigoPedido);

    void cancelamento(String codigoPedido);

}
