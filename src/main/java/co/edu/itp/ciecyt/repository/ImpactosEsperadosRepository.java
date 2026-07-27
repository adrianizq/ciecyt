package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.domain.ResultadosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ImpactosEsperados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpactosEsperadosRepository extends JpaRepository<ImpactosEsperados, Long> {
    List<ImpactosEsperados> findByImpactosEsperadoProyectoIdOrderByOrdenVista(Long idProyecto);

}
