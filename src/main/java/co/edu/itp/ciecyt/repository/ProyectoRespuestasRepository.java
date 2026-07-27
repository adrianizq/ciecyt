package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.domain.ProyectoRespuestas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ProyectoRespuestas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoRespuestasRepository extends JpaRepository<ProyectoRespuestas, Long> {
    List<ProyectoRespuestas> findByProyectoRespuestasProyectoIdOrderByProyectoRespuestasProyectoId(Long idProyecto);
    List<ProyectoRespuestas> findByProyectoRespuestasProyectoIdAndFaseIdAndAuthority(Long idProyecto, Long faseId, String authority);
}
