package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Menu;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import java.util.List;
import java.util.Optional;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Modalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Long> {
    public List<Modalidad> findAll();
}
