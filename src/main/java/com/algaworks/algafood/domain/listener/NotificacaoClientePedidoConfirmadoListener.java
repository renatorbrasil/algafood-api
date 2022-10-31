package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.core.email.EmailTemplateBuilder;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateBuilder templateBuilder;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        var corpo = templateBuilder.processarTemplate("emails/pedido-confirmado.html", pedido);

        var mensagem = EmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmdo")
                .corpo(corpo)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        emailService.enviar(mensagem);
    }
}
