package com.algaworks.algafood.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecsBuilder<T> {
    public Specification<T> and(List<Specification<T>> specs) {

        Specification<T> finalSpec = null;
        for (Specification<T> spec : specs) {
            if (finalSpec == null) {
                finalSpec = spec;
            } else {
                finalSpec = finalSpec.and(spec);
            }

        }

        return finalSpec;
    }
}
