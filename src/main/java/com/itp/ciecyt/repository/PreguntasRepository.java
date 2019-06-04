package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Preguntas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Preguntas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntasRepository extends JpaRepository<Preguntas, Long> {

}
