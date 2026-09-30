package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.DecanoFacultad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DecanoFacultad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecanoFacultadRepository extends JpaRepository<DecanoFacultad, Long> {

    /**
     * Decano vigente de una facultad. Solo puede existir uno: lo garantiza el indice parcial
     * ux_decano_facultad_vigente, por eso se consulta con fechaHasta nula.
     */
    @Query("select d from DecanoFacultad d where d.facultad.id = :facultadId and d.fechaHasta is null")
    Optional<DecanoFacultad> findDecanoVigenteDeFacultad(@Param("facultadId") Long facultadId);

    /**
     * Facultades en las que la persona actua como decano o como su delegado.
     */
    @Query("select d from DecanoFacultad d where d.user.login = :login and d.fechaHasta is null")
    List<DecanoFacultad> findVigentesByLogin(@Param("login") String login);

    List<DecanoFacultad> findByFacultadId(Long facultadId);

    List<DecanoFacultad> findByUserId(Long userId);
}
