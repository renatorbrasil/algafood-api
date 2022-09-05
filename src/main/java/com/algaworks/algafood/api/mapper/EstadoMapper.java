package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.api.dto.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoMapper extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoMapper() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = modelMapper.map(estado, EstadoModel.class);
        estadoModel.add(linkTo(methodOn(EstadoController.class).buscar(estadoModel.getId()))
                .withSelfRel());

        estadoModel.add(linkTo(methodOn(EstadoController.class).listar())
                .withRel(IanaLinkRelations.COLLECTION));

        return estadoModel;
    }


    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> estados) {
        return super.toCollectionModel(estados)
                .add(linkTo(EstadoController.class).withSelfRel());
    }

    public Estado toDomain(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyDtoToDomain(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
