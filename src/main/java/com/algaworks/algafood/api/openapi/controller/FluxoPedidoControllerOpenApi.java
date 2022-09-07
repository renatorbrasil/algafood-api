package com.algaworks.algafood.api.openapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedido")
public interface FluxoPedidoControllerOpenApi {

    ResponseEntity<Void> confirmar(String codigoPedido);

    ResponseEntity<Void> entrega(String codigoPedido);

    ResponseEntity<Void> cancelamento(String codigoPedido);

}
