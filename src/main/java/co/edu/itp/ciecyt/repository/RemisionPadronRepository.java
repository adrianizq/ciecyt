package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.RemisionPadron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Consulta de las remisiones del padron enviadas a CIECYT.
 */
public interface RemisionPadronRepository extends JpaRepository<RemisionPadron, Long> {

    /**
     * Remisiones de una facultad, la mas reciente primero. Incluye los borradores, que son los
     * borradores de esa misma decanatura.
     */
    @Query("select r from RemisionPadron r where r.facultad.id = :facultadId order by r.fechaRemision desc")
    List<RemisionPadron> findPorFacultad(@Param("facultadId") Long facultadId);

    /**
     * Solo las ya remitidas, que son las que hacen fe.
     */
    @Query("select r from RemisionPadron r where r.facultad.id = :facultadId and r.estado = 'ENVIADO' order by r.fechaRemision desc")
    List<RemisionPadron> findEnviadasPorFacultad(@Param("facultadId") Long facultadId);

    /**
     * Todas las remisiones enviadas de la institucion, que es lo que el CIECYT recibe.
     */
    @Query("select r from RemisionPadron r where r.estado = 'ENVIADO' order by r.facultad.id, r.fechaRemision desc")
    List<RemisionPadron> findTodasEnviadas();

    @Query("select r from RemisionPadron r where r.facultad.id = :facultadId and r.periodo = :periodo and r.estado = 'ENVIADO'")
    List<RemisionPadron> findEnviadaDelPeriodo(@Param("facultadId") Long facultadId, @Param("periodo") String periodo);

    Optional<RemisionPadron> findByIdAndFacultadId(Long id, Long facultadId);
}
