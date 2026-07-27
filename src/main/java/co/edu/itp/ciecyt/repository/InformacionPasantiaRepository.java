package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.domain.InformacionPasantia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the InformacionPasantia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformacionPasantiaRepository extends JpaRepository<InformacionPasantia, Long> {
    InformacionPasantia findByInformacionPasantiaProyectoId(Long idProyecto);
}
