package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.dto.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;
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
public class CidadeMapper extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeMapper() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId()))
                .withSelfRel());

        cidadeModel.add(linkTo(methodOn(CidadeController.class).listar())
                .withRel(IanaLinkRelations.COLLECTION));

        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel());

        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).listar())
                .withRel(IanaLinkRelations.COLLECTION));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

    public Cidade toDomain(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyDtoToDomain(CidadeInput cidadeInput, Cidade cidade) {
        /*
         * Essa próxima linha é para evitar a exception:
         * org.hibernate.HibernateException: indentifier of an instance of
         * com.algaworks.algafood.domain.model.Estado was altered from 1 to 2.
         */
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }
}
