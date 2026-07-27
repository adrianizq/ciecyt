package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Cronograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * Spring Data  repository for the Cronograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {
    List<Cronograma> findByCronogramaProyectoIdOrderByOrdenVista(Long idProyecto);

}
