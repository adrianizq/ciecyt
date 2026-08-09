package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the ProyectoHistorialEstado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoHistorialEstadoRepository extends JpaRepository<ProyectoHistorialEstado, Long> {

    @Query("SELECT h FROM ProyectoHistorialEstado h WHERE h.proyecto.id = :proyectoId ORDER BY h.fechaCambio DESC")
    List<ProyectoHistorialEstado> findByProyectoIdOrderByFechaCambioDesc(@Param("proyectoId") Long proyectoId);
}
