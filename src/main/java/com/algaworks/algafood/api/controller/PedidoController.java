package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.api.dto.model.PedidoResumoModel;
import com.algaworks.algafood.api.mapper.PedidoMapper;
import com.algaworks.algafood.api.mapper.PedidoResumoMapper;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private CadastroPedidoService cadastroPedido;

	@Autowired
	private PedidoMapper pedidoMapper;

	@Autowired
	private PedidoResumoMapper pedidoResumoMapper;

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		Page<Pedido> pedidosPage = pedidoRepository
				.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

		return pagedResourcesAssembler.toModel(
				new PageImpl<>(pedidosPage.getContent(), pageable, pedidosPage.getTotalElements()),
				pedidoResumoMapper);
	}


	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedido.buscar(codigoPedido);
		return pedidoMapper.toModel(pedido);
	}

	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoMapper.toDomain(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

			novoPedido = emissaoPedido.emitir(novoPedido);
			return pedidoMapper.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"cliente.id", "cliente.id",
				"nomerestaurante", "restaurante.nome",
				"valorTotal", "valorTotal");

		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}
