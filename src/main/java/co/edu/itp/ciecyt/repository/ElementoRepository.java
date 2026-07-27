package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.Elemento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Elemento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementoRepository extends JpaRepository<Elemento, Long> {

    //List<Elemento> findByElementoModalidadId(Long idModalidad);
    List<Elemento> findByElementoFasesIdOrderByOrden(Long idFase);
    List<Elemento> findAllByOrderByIdAsc();
    //List<Elemento> findByElementoFasesIdAndElementoFormatoId(Long idFase, Long idFormato);

    List<Elemento> findByElementoFasesId(Long id);

}
