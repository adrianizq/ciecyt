package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.IntegrantesProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the IntegrantesProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrantesProyectoRepository extends JpaRepository<IntegrantesProyecto, Long> {

    @Query("select integrantesProyecto from IntegrantesProyecto integrantesProyecto where integrantesProyecto.user.login = ?#{principal.username}")
    List<IntegrantesProyecto> findByUserIsCurrentUser();

}
