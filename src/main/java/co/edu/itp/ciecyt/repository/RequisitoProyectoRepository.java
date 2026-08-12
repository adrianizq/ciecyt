package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.RequisitoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the RequisitoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitoProyectoRepository extends JpaRepository<RequisitoProyecto, Long> {

    List<RequisitoProyecto> findByRequisitoProyectoProyectoId(Long proyectoId);

    Optional<RequisitoProyecto> findByRequisitoProyectoProyectoIdAndRequisitoProyectoRequisitoId(
        Long proyectoId,
        Long requisitoId
    );

    List<RequisitoProyecto> findByRequisitoProyectoProyectoIdAndEstado(
        Long proyectoId,
        EnumEstadoRequisito estado
    );

    boolean existsByRequisitoProyectoProyectoIdAndRequisitoProyectoRequisitoId(
        Long proyectoId,
        Long requisitoId
    );
}
