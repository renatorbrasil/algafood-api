package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscar(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void entrega(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelamento(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscar(codigoPedido);
        pedido.cancelar();
    }
}
