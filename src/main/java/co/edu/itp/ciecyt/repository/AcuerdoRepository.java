package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Acuerdo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Acuerdo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcuerdoRepository extends JpaRepository<Acuerdo, Long> {

}
