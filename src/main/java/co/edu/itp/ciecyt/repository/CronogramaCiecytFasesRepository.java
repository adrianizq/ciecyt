package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.CronogramaCiecytFases;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CronogramaCiecytFases entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CronogramaCiecytFasesRepository extends JpaRepository<CronogramaCiecytFases, Long> {

}
