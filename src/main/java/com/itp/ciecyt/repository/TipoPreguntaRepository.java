package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.TipoPregunta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoPregunta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPreguntaRepository extends JpaRepository<TipoPregunta, Long> {

}
