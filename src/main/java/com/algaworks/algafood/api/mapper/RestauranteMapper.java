package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.input.RestauranteInput;
import com.algaworks.algafood.api.dto.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel map(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

    public List<RestauranteModel> map(List<Restaurante> restaurantes) {
        return restaurantes
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Restaurante map(RestauranteInput restauranteInputDto) {
        return modelMapper.map(restauranteInputDto, Restaurante.class);
    }

    public void copyDtoToDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
        /*
         * Essa próxima linha é para evitar a exception:
         * org.hibernate.HibernateException: indentifier of an instance of
         * com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2.
         */
        restaurante.setCozinha(new Cozinha());

        /*
         * Esse próximo trecho é para evitar a exception:
         * org.hibernate.HibernateException: indentifier of an instance of
         * com.algaworks.algafood.domain.model.Cidade was altered from 1 to 2.
         */
        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
