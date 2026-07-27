package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Entidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Long> {

}
