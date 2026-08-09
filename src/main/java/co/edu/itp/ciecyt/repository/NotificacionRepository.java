package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Notificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    @Query("SELECT n FROM Notificacion n WHERE n.user.login = :login AND n.leido = false ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByUserLoginAndLeidoFalseOrderByFechaCreacionDesc(@Param("login") String login);

    @Query("SELECT n FROM Notificacion n WHERE n.user.id = :userId ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByUserIdOrderByFechaCreacionDesc(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Notificacion n SET n.leido = true, n.fechaLectura = CURRENT_TIMESTAMP WHERE n.id = :id AND n.user.login = :login")
    int marcarLeida(@Param("id") Long id, @Param("login") String login);
}
