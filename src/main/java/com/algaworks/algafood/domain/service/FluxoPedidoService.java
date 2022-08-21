package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscar(codigoPedido);
        pedido.confirmar();

        // Necessário para disparar o evento da entidade.
        pedidoRepository.save(pedido);
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

        // Necessário para disparar o evento da entidade.
        pedidoRepository.save(pedido);
    }
}
