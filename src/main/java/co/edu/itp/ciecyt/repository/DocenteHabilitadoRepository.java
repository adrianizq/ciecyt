package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.DocenteHabilitado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Consulta del padron de profesores habilitados.
 *
 * <p>Las listas se piden por facultad porque la habilitacion es por facultad: un docente puede
 * estar habilitado en una y no en otra, y el CIECYT solo puede designar de la lista de la
 * facultad a la que pertenece el proyecto.
 */
public interface DocenteHabilitadoRepository extends JpaRepository<DocenteHabilitado, Long> {

    /**
     * Los habilitados vigentes de una facultad para un rol. Es la lista de la que se puede
     * designar, y por eso solo trae los que no tienen fecha de cierre.
     */
    @Query("select d from DocenteHabilitado d where d.facultad.id = :facultadId and d.rol = :rol and d.fechaHasta is null order by d.user.id")
    List<DocenteHabilitado> findVigentesPorFacultadYRol(@Param("facultadId") Long facultadId, @Param("rol") String rol);

    /**
     * Todos los vigentes de una facultad, sin filtrar por rol.
     */
    @Query("select d from DocenteHabilitado d where d.facultad.id = :facultadId and d.fechaHasta is null order by d.rol, d.user.id")
    List<DocenteHabilitado> findVigentesPorFacultad(@Param("facultadId") Long facultadId);

    /**
     * Si una persona esta habilitada hoy para un rol en una facultad. Es la pregunta que se hace
     * al designar, y se responde en una sola consulta para no cargar la lista completa.
     */
    @Query("select (count(d) > 0) from DocenteHabilitado d where d.user.id = :userId and d.facultad.id = :facultadId and d.rol = :rol and d.fechaHasta is null")
    boolean esVigente(@Param("userId") Long userId, @Param("facultadId") Long facultadId, @Param("rol") String rol);

    /**
     * El registro vigente de esa persona en esa facultad, para poder cerrarlo cuando deja de estar
     * habilitada en vez de borrar el historico.
     */
    @Query("select d from DocenteHabilitado d where d.user.id = :userId and d.facultad.id = :facultadId and d.rol = :rol and d.fechaHasta is null")
    List<DocenteHabilitado> findVigente(@Param("userId") Long userId, @Param("facultadId") Long facultadId, @Param("rol") String rol);

    /**
     * El historial de una persona dentro de una sola facultad. La habilitacion es por facultad, y
     * quien consulta solo puede ver las facultades autorizadas, asi que el historial se tiene que
     * poder pedir asi: si se pidiera por persona y sin facultad, bastaria que una de sus
     * habilitaciones cayera en la facultad del que pregunta para que le llegaran tambien las de
     * las otras.
     */
    @Query("select d from DocenteHabilitado d where d.user.id = :userId and d.facultad.id = :facultadId order by d.rol, d.fechaDesde")
    List<DocenteHabilitado> findHistorialDeUsuarioEnFacultad(@Param("userId") Long userId, @Param("facultadId") Long facultadId);
}
