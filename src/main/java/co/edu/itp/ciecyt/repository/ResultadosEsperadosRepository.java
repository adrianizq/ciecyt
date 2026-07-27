package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.ResultadosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ResultadosEsperados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadosEsperadosRepository extends JpaRepository<ResultadosEsperados, Long> {
    List<ResultadosEsperados> findByResultadosEsperadosProyectoIdOrderByOrdenVista(Long idProyecto);

}
