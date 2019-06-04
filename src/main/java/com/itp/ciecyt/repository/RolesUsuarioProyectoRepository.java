package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.RolesUsuarioProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RolesUsuarioProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolesUsuarioProyectoRepository extends JpaRepository<RolesUsuarioProyecto, Long> {

}
