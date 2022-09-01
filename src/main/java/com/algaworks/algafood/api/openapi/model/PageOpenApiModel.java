package com.algaworks.algafood.api.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(name = "PageModel")
@Getter
public class PageOpenApiModel {
    private List<?> content;

    @Schema(example = "10")
    private Long size;

    @Schema(example = "20")
    private Long totalElements;

    @Schema(example = "2")
    private Long totalPages;

    @Schema(example = "0")
    private Long number;
}
