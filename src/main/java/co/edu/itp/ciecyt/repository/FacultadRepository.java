package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Facultad;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Facultad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {
    public List<Facultad> findAll();
}
