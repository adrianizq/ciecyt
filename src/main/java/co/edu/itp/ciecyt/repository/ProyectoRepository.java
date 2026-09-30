package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Spring Data  repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

   Proyecto findByIdOrderById(Long idProyecto);

    List<Proyecto> findByFacultadIdAndProyectoModalidadId(Long facultad, Long modalidad);

    /**
     * Proyectos de un conjunto de facultades. Es la consulta del alcance de la decanura: la
     * lista de facultades llega desde la asignacion vigente del usuario y no desde el codigo.
     */
    List<Proyecto> findByFacultadIdIn(List<Long> facultadIds);

    Page<Proyecto> findByFacultadIdIn(List<Long> facultadIds, Pageable pageable);

    List<Proyecto> findAll();

    List<Proyecto> findByTituloContainingIgnoreCase(String titulo);

    //List<Proyecto> findByProgramaContainingIgnoreCase(String programa);

    List<Proyecto> findByProyectoProgramaId(Long idPrograma);
}
