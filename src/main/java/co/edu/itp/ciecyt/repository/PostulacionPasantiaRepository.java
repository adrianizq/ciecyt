package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.PostulacionPasantia;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoPostulacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the PostulacionPasantia entity.
 */
@Repository
public interface PostulacionPasantiaRepository extends JpaRepository<PostulacionPasantia, Long> {
    List<PostulacionPasantia> findByConvocatoriaIdOrderByFechaPostulacionDesc(Long convocatoriaId);

    List<PostulacionPasantia> findByUsuarioIdOrderByFechaPostulacionDesc(Long usuarioId);

    Optional<PostulacionPasantia> findByConvocatoriaIdAndProyectoId(Long convocatoriaId, Long proyectoId);

    long countByConvocatoriaIdAndEstado(Long convocatoriaId, EnumEstadoPostulacion estado);

    boolean existsByProyectoIdAndEstado(Long proyectoId, EnumEstadoPostulacion estado);
}
