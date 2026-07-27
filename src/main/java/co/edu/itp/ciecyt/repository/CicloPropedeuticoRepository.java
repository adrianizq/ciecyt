package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.CicloPropedeutico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CicloPropedeutico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicloPropedeuticoRepository extends JpaRepository<CicloPropedeutico, Long> {

}
