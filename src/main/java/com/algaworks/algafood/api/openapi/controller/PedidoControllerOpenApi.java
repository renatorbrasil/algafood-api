package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.api.dto.model.PedidoResumoModel;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Pedido")
public interface PedidoControllerOpenApi {

    @PageableAsQueryParam
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, @Parameter(hidden = true) Pageable pageable);

    PedidoModel buscar(String codigoPedido);

    PedidoModel adicionar(PedidoInput pedidoInput);

}
