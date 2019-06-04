package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Proyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

}
