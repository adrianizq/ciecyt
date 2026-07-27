package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.RolesModalidad;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RolesModalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolesModalidadRepository extends JpaRepository<RolesModalidad, Long> {
    RolesModalidad findByRolAndRolesModalidadModalidadId(String rol, Long rolModalidad);

    List<RolesModalidad> findByRolesModalidadAuthorityName(String authority);
    List<RolesModalidad> findByRol(String rol);
}
