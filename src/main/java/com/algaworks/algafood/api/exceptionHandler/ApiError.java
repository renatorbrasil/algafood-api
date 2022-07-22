package com.algaworks.algafood.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private OffsetDateTime timestamp;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {
        private String name;
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
