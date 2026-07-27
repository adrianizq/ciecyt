package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.ElementoModalidad;

import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ElementoModalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementoModalidadRepository extends JpaRepository<ElementoModalidad, Long> {
    List<ElementoModalidad> findByModalidadId(Long idModalidad);
    List<ElementoModalidad> findByElementoId(Long idElemento);
    ElementoModalidad findByElementoIdAndModalidadId(Long idElemento,Long idModalidad);
}
