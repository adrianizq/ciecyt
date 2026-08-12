package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Requisito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Requisito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {

    List<Requisito> findAllByActivoTrueOrderByNombreAsc();

    List<Requisito> findByRequisitoModalidadIdAndActivoTrueOrderByNombreAsc(Long modalidadId);

    List<Requisito> findByRequisitoModalidadIsNullAndActivoTrueOrderByNombreAsc();

    List<Requisito> findByRequisitoModalidadIdOrRequisitoModalidadIsNullOrderByNombreAsc(Long modalidadId);

    boolean existsByCodigo(String codigo);
}
