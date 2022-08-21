package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService implements EmailService {
    @Override
    public void enviar(Mensagem mensagem) {
        log.info("[FAKE E-MAIL] Para {}\n{}", mensagem.getDestinatarios(), mensagem.getCorpo());
    }
}
