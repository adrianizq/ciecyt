package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Solicitud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    @Query(
        "SELECT s FROM Solicitud s WHERE s.solicitudIntegranteProyecto.integranteProyectoProyecto.id = :proyectoId " +
        "ORDER BY s.fechaSolicitud DESC, s.id DESC"
    )
    List<Solicitud> findByProyectoId(@Param("proyectoId") Long proyectoId);
}
