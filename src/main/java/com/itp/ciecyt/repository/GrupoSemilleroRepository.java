package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.GrupoSemillero;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrupoSemillero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoSemilleroRepository extends JpaRepository<GrupoSemillero, Long> {

}
