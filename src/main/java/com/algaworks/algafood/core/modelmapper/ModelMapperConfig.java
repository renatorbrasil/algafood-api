package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.dto.input.ItemPedidoInput;
import com.algaworks.algafood.api.dto.model.EnderecoModel;
import com.algaworks.algafood.api.dto.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                        .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
                .<String>addMapping(src -> src.getCidade().getEstado().getNome(),
                        (dest, value) -> dest.getCidade().setEstado(value));

        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setTaxaFrete);

        return modelMapper;
    }
}
