package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired EmailProperties emailProperties;

    @Bean
    public EmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEmailService();
            case SMTP:
                return new SmtpEmailService();
            default:
                return null;
        }
    }
}
