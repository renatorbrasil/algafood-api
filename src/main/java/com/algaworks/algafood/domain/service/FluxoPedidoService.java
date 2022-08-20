package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.email.EmailTemplateBuilder;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private EnvioEmailService emailService;

    @Autowired
    private EmailTemplateBuilder templateBuilder;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscar(codigoPedido);
        pedido.confirmar();

        var corpo = templateBuilder.processarTemplate("pedido-confirmado.html", pedido);

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmdo")
                .corpo(corpo)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        emailService.enviar(mensagem);
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
