package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Programa;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Programa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {

    @Query("SELECT p FROM Programa p WHERE LOWER(p.ciclo) = LOWER(:ciclo)")
    List<Programa> findByCiclo(@Param("ciclo") String ciclo);
}
