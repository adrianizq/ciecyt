package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.AsesorExterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Profesionales externos gestionados por el CIECYT cuando la lista habilitada no tiene
 * disponibilidad (Acuerdo 25, paragrafos 1 de los articulos 8 y 9).
 *
 * <p>Se listan por facultad igual que el padron de habilitados: quien va a designar elige
 * dentro de la misma facultad a la que pertenece el proyecto.
 */
public interface AsesorExternoRepository extends JpaRepository<AsesorExterno, Long> {

    List<AsesorExterno> findByFacultadId(Long facultadId);

    /**
     * Los externos de una facultad para un rol que han pasado la verificacion de idoneidad del
     * CIECYT; solo esos se pueden designar.
     */
    @Query("select e from AsesorExterno e where e.facultad.id = :facultadId and e.rol = :rol and e.idoneidadVerificada = true"
        + " order by e.apellidos, e.nombres")
    List<AsesorExterno> findVerificadosPorFacultadYRol(@Param("facultadId") Long facultadId, @Param("rol") String rol);

    /**
     * Evita duplicar a la misma persona sin querer dentro de una facultad.
     */
    @Query("select e from AsesorExterno e where e.facultad.id = :facultadId and lower(e.numeroDocumento) = lower(:documento)")
    Optional<AsesorExterno> findPorDocumentoEnFacultad(@Param("facultadId") Long facultadId, @Param("documento") String documento);
}