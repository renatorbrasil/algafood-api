package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends
        CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    /*
    * Named query: Restaurante.consultarPorNome
    * */
    List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinha);

    @Query("from Restaurante r join fetch r.cozinha")
    List<Restaurante> findAll();

}
