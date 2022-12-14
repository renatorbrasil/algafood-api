package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter @Setter
@Component @Validated
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    private Implementacao impl = Implementacao.FAKE;

    @NotNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE
    }
}
