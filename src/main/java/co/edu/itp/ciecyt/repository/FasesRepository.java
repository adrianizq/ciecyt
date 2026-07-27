package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Fases;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Fases entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FasesRepository extends JpaRepository<Fases, Long> {
     Fases findByFase(String fase);
      //List<Fases> findByFasesModalidadId(Long idModalidad);

}
