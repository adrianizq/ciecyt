package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Retroalimentacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Retroalimentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetroalimentacionRepository extends JpaRepository<Retroalimentacion, Long> {

    @Query("select retroalimentacion from Retroalimentacion retroalimentacion where retroalimentacion.retroalimentacionUser.login = ?#{principal.username}")
    List<Retroalimentacion> findByRetroalimentacionUserIsCurrentUser();

}
