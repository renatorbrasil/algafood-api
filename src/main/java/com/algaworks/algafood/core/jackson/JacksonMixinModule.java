package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.dto.mixin.CidadeJacksonMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeJacksonMixin.class);
    }
}
