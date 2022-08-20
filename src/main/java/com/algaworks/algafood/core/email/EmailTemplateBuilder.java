package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infrastructure.service.email.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailTemplateBuilder {

    @Autowired
    private Configuration freemarkerConfig;

    public String processarTemplate(String templatePath, Pedido pedido) {
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("pedido", pedido);
            Template template = freemarkerConfig.getTemplate(templatePath);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do email", e);
        }
    }
}
