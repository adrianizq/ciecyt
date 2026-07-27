package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Categorizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Categorizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorizacionRepository extends JpaRepository<Categorizacion, Long> {

}
