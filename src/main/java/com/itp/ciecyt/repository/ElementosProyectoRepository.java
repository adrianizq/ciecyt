package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.ElementosProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ElementosProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementosProyectoRepository extends JpaRepository<ElementosProyecto, Long> {

}
