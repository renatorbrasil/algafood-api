package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.RestauranteInput;
import com.algaworks.algafood.api.dto.RestauranteModel;
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

    public RestauranteModel domainToDto(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

    public List<RestauranteModel> domainToDto(List<Restaurante> restaurantes) {
        return restaurantes
                .stream()
                .map(this::domainToDto)
                .collect(Collectors.toList());
    }

    public Restaurante dtoToDomain(RestauranteInput restauranteInputDto) {
        return modelMapper.map(restauranteInputDto, Restaurante.class);
    }

    public void copyDtoToDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
        /*
         * Essa próxima linha é para evitar a exception:
         * org.hibernate.HibernateException: indentifier of an instance of
         * com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2.
         */
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInput, restaurante);
    }
}
