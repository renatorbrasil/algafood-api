package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomiser customizeGlobalHttpStatus() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse(
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        new ApiResponse().description("Erro interno do servidor"));
                apiResponses.addApiResponse(
                        String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                        new ApiResponse().description("Recurso não possui representação que pôde ser aceita pelo consumidor"));
                apiResponses.addApiResponse(
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        new ApiResponse().description("Requisição inválida (erro do cliente)"));
                apiResponses.addApiResponse(
                        String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),
                        new ApiResponse().description("Requisição recusada porque o corpo está em um formato não suportado"));

            }));
        };
    }

    @Bean
    public OpenApiCustomiser addApiErrorModel() {
        Map<String, Schema> schemasToAdd = new HashMap<>(ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ApiError.class))
                .referencedSchemas);
        return openApi -> {
            var existingSchemas = openApi.getComponents().getSchemas();
            if (!CollectionUtils.isEmpty(existingSchemas)) {
                schemasToAdd.putAll(existingSchemas);
            }
            openApi.getComponents().setSchemas(schemasToAdd);
        };
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Algafood API")
                .description("API aberta para clientes e restaurantes")
                .version("1"));
    }
}
