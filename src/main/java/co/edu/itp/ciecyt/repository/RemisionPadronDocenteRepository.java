package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.RemisionPadronDocente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RemisionPadronDocenteRepository extends JpaRepository<RemisionPadronDocente, Long> {

    @Query("select d from RemisionPadronDocente d where d.remision.id = :remisionId order by d.rol, d.nombreAlRemitir")
    List<RemisionPadronDocente> findPorRemision(@Param("remisionId") Long remisionId);
}
