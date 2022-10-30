package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cozinhas {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface Restaurantes {
        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeGerenciarCadastro { }

        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface Pedidos {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or " +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeBuscar { }

        @PostAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodePesquisar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeCriar { }

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface Cidades {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface Estados {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface UsuariosGruposPermissoes {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeAlterarPropriaSenha { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeAlterarUsuario { }

        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

    @interface Estatisticas {
        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface PodeConsultar { }
    }

}
