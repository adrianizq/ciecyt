package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.PreguntaRespuesta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PreguntaRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntaRespuestaRepository extends JpaRepository<PreguntaRespuesta, Long> {

}
