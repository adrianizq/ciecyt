package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ElementoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementoProyectoRepository extends JpaRepository<ElementoProyecto, Long> {
    //List<ElementoProyecto> findByElementoProyectoProyectoId(Long idProyecto);
    List<ElementoProyecto> findByElementoProyectoProyectoIdOrderByElementoProyectoElementoId(Long idProyecto);
    List<ElementoProyecto> findByElementoProyectoProyectoIdAndElementoFasesIdOrderByElementoProyectoElementoId(Long idProyecto, Long idFase);


}
