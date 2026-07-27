package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.ProyectoFase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProyectoFase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoFaseRepository extends JpaRepository<ProyectoFase, Long> {

}
