package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível", HttpStatus.BAD_REQUEST),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado", HttpStatus.NOT_FOUND),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso", HttpStatus.CONFLICT),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio", HttpStatus.BAD_REQUEST),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido", HttpStatus.BAD_REQUEST),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos", HttpStatus.BAD_REQUEST);

    private final String title;
    private final String path;
    private final HttpStatus status;

    ApiErrorType(String path, String title, HttpStatus status) {
        this.path = path;
        this.title = title;
        this.status = status;
    }

    public int getStatusCode() {
        return this.status.value();
    }
}
