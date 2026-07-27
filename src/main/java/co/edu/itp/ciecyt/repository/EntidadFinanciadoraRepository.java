package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.EntidadFinanciadora;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the EntidadFinanciadora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadFinanciadoraRepository extends JpaRepository<EntidadFinanciadora, Long> {
    List<EntidadFinanciadora> findByEntidadFinanciadoraProyectoId(Long idProyecto);

}
