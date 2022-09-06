package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.input.CozinhaInput;
import com.algaworks.algafood.api.dto.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaMapper extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaMapper() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = modelMapper.map(cozinha, CozinhaModel.class);

        cozinhaModel.add(linkTo(methodOn(CozinhaController.class).buscar(cozinha.getId()))
                .withSelfRel());

        cozinhaModel.add(linkTo(CozinhaController.class)
                .withRel(IanaLinkRelations.COLLECTION));

        return cozinhaModel;
    }

    public Cozinha toDomain(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyDtoToDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
