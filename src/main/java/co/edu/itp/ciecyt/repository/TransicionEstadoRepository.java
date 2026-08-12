package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.TransicionEstado;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TransicionEstado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransicionEstadoRepository extends JpaRepository<TransicionEstado, Long> {

    List<TransicionEstado> findByTransicionEstadoModalidadIdAndActivoTrue(Long modalidadId);

    List<TransicionEstado> findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(
        Long modalidadId,
        EnumEstadoProyecto estadoOrigen
    );

    boolean existsByTransicionEstadoModalidadIdAndEstadoOrigenAndEstadoDestino(
        Long modalidadId,
        EnumEstadoProyecto estadoOrigen,
        EnumEstadoProyecto estadoDestino
    );
}
