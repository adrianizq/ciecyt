package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Ciclo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ciclo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Long> {
    List<Ciclo> findAll();
}
