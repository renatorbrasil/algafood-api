package com.algaworks.algafood.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@Schema(name = "Problema")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "2022-08-31T22:27:06.10612Z")
    private OffsetDateTime timestamp;

    @Schema(name = "Lista de objetos ou campos que geraram o erro (opcional)")
    private List<Object> objects;

    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "estado")
        private String name;

        @Schema(example = "Campo Estado da cidade é obrigatório")
        private String userMessage;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    private static String BASE_URI = "https://algafood.com.br";

    public static ApiError fromApiErrorType(ApiErrorType error, String message) {
        return ApiError.builder()
                .detail(message)
                .title(error.getTitle())
                .timestamp(OffsetDateTime.now())
                .type(BASE_URI + error.getPath())
                .status(error.getStatusCode())
                .build();
    }

    public static ApiError fromHttpStatus(HttpStatus status, String message) {
        return ApiError.builder()
                .detail(message)
                .title(status.getReasonPhrase())
                .timestamp(OffsetDateTime.now())
                .type(BASE_URI + "/" +
                        status.name().replace("_", "-").toLowerCase(Locale.ROOT))
                .status(status.value())
                .build();
    }
}
