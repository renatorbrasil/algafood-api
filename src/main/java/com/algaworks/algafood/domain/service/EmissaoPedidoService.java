package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    public Pedido buscar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);
        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProduto.buscar(
                    pedido.getRestaurante().getId(),
                    item.getProduto().getId()
            );
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    private void validarPedido(Pedido pedido) {
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        Cidade cidade = cadastroCidade.buscar(cidadeId);

        Long clienteId = pedido.getCliente().getId();
        Usuario cliente = cadastroUsuario.buscar(clienteId);

        Long restauranteId = pedido.getRestaurante().getId();
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
        if (!restaurante.aceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format(
                    "Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
    }
}
