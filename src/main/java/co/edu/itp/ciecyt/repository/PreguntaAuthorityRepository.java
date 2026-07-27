package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.PreguntaAuthority;

import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PreguntaAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntaAuthorityRepository extends JpaRepository<PreguntaAuthority, Long> {
    List<PreguntaAuthority> findByAuthorityName(String authority);
    List<PreguntaAuthority> findByPregunta3Id(Long idPregunta);
}
