package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.FichaTecnica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FichaTecnica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

    @Query("select fichaTecnica from FichaTecnica fichaTecnica where fichaTecnica.user.login = ?#{principal.username}")
    List<FichaTecnica> findByUserIsCurrentUser();

}
