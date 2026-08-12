package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.EstadoModalidad;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the EstadoModalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoModalidadRepository extends JpaRepository<EstadoModalidad, Long> {

    List<EstadoModalidad> findByEstadoModalidadModalidadIdOrderByOrdenAsc(Long modalidadId);

    List<EstadoModalidad> findByEstadoModalidadModalidadIdAndActivoTrueOrderByOrdenAsc(Long modalidadId);

    List<EstadoModalidad> findByEstadoModalidadModalidadIdAndEstadoOrderByOrdenAsc(Long modalidadId, EnumEstadoProyecto estado);
}
