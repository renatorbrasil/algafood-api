package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.dto.model.PedidoModel;
import com.algaworks.algafood.api.dto.model.PedidoResumoModel;
import com.algaworks.algafood.api.mapper.PedidoMapper;
import com.algaworks.algafood.api.mapper.PedidoResumoMapper;
import com.algaworks.algafood.core.data.PageableTranslator;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

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

	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
		pageable = traduzirPageable(pageable);
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		List<PedidoResumoModel> pedidosModel = pedidoResumoMapper.map(pedidosPage.getContent());
		return new PageImpl<>(pedidosModel, pageable, pedidosPage.getTotalElements());
	}


	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedido.buscar(codigoPedido);
		return pedidoMapper.map(pedido);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoMapper.map(pedidoInput);

			// TODO: pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);
			return pedidoMapper.map(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"cliente.id", "cliente.id",
				"restaurante.nome", "restautante.nome",
				"valorTotal", "valorTotal");

		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}
