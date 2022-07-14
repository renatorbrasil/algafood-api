package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeJacksonMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
