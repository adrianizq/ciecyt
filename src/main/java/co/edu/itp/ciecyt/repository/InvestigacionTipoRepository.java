package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.InvestigacionTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InvestigacionTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvestigacionTipoRepository extends JpaRepository<InvestigacionTipo, Long> {
     InvestigacionTipo findByInvestigacionTipo(String tipo);
}
