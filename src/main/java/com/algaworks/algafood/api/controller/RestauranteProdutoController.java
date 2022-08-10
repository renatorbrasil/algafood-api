package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.input.ProdutoInput;
import com.algaworks.algafood.api.dto.model.ProdutoModel;
import com.algaworks.algafood.api.mapper.ProdutoMapper;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private ProdutoMapper produtoMapper;

	@GetMapping
	public List<ProdutoModel> listar(
			@PathVariable Long restauranteId,
			@RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		List<Produto> produtos = incluirInativos
				? produtoRepository.findByRestaurante(restaurante)
				: produtoRepository.findAtivosByRestaurante(restaurante);

		return produtoMapper.map(produtos);
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
		return produtoMapper.map(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId,
								  @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		Produto produto = produtoMapper.map(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProduto.salvar(produto);
		return produtoMapper.map(produto);
	}

	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId,
								  @PathVariable Long produtoId,
								  @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProduto.buscar(restauranteId, produtoId);
		produtoMapper.copyDtoToDomain(produtoInput, produtoAtual);
		produtoAtual = cadastroProduto.salvar(produtoAtual);
		return produtoMapper.map(produtoAtual);
	}

}
