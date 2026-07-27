package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.AdjuntoProyectoFase;

import co.edu.itp.ciecyt.domain.Elemento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AdjuntoProyectoFase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjuntoProyectoFaseRepository extends JpaRepository<AdjuntoProyectoFase, Long> {
    List<AdjuntoProyectoFase> findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(Long idProyecto, Long idFase);
}
