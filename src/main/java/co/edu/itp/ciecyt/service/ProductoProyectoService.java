package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ProductoProyectoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ProductoProyecto}.
 */
public interface ProductoProyectoService {

    /**
     * Save a productoProyecto.
     *
     * @param productoProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoProyectoDTO save(ProductoProyectoDTO productoProyectoDTO);

    /**
     * Get all the productoProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoProyectoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productoProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoProyectoDTO> findOne(Long id);

    /**
     * Delete the "id" productoProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
