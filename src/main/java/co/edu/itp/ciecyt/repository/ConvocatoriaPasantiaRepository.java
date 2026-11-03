package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.ConvocatoriaPasantia;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoConvocatoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the ConvocatoriaPasantia entity.
 */
@Repository
public interface ConvocatoriaPasantiaRepository extends JpaRepository<ConvocatoriaPasantia, Long> {
    List<ConvocatoriaPasantia> findAllByOrderByFechaCierreDesc();

    List<ConvocatoriaPasantia> findByEstadoAndActivoTrueOrderByFechaCierreAsc(EnumEstadoConvocatoria estado);
}
