package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.CronogramaCiecyt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CronogramaCiecyt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CronogramaCiecytRepository extends JpaRepository<CronogramaCiecyt, Long> {

}
