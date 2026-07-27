package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PreguntaModalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntaModalidadRepository extends JpaRepository<PreguntaModalidad, Long> {
    List<PreguntaModalidad> findByModalidad2Id(Long idModalidad);
    List<PreguntaModalidad> findByPreguntaId(Long idPregunta);
    PreguntaModalidad findByPreguntaIdAndModalidad2Id(Long idPregunta,Long idModalidad);
}
