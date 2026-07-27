package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.AdjuntoProyectoFase;
import co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AdjuntoRetroalimentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjuntoRetroalimentacionRepository extends JpaRepository<AdjuntoRetroalimentacion, Long> {

    List<AdjuntoRetroalimentacion> findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(Long idProyecto, Long idFase, String authority);

}
