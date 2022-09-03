package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entrega(@PathVariable String codigoPedido) {
        fluxoPedidoService.entrega(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelamento(@PathVariable String codigoPedido) {
        fluxoPedidoService.cancelamento(codigoPedido);
    }
}
