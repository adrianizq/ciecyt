package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.LineaInvestigacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LineaInvestigacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineaInvestigacionRepository extends JpaRepository<LineaInvestigacion, Long> {

}
