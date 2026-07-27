package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.ProductoProyectoService;
import co.edu.itp.ciecyt.domain.ProductoProyecto;
import co.edu.itp.ciecyt.repository.ProductoProyectoRepository;
import co.edu.itp.ciecyt.service.dto.ProductoProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.ProductoProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductoProyecto}.
 */
@Service
@Transactional
public class ProductoProyectoServiceImpl implements ProductoProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProductoProyectoServiceImpl.class);

    private final ProductoProyectoRepository productoProyectoRepository;

    private final ProductoProyectoMapper productoProyectoMapper;

    public ProductoProyectoServiceImpl(ProductoProyectoRepository productoProyectoRepository, ProductoProyectoMapper productoProyectoMapper) {
        this.productoProyectoRepository = productoProyectoRepository;
        this.productoProyectoMapper = productoProyectoMapper;
    }

    /**
     * Save a productoProyecto.
     *
     * @param productoProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoProyectoDTO save(ProductoProyectoDTO productoProyectoDTO) {
        log.debug("Request to save ProductoProyecto : {}", productoProyectoDTO);
        ProductoProyecto productoProyecto = productoProyectoMapper.toEntity(productoProyectoDTO);
        productoProyecto = productoProyectoRepository.save(productoProyecto);
        return productoProyectoMapper.toDto(productoProyecto);
    }

    /**
     * Get all the productoProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoProyectos");
        return productoProyectoRepository.findAll(pageable)
            .map(productoProyectoMapper::toDto);
    }


    /**
     * Get one productoProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoProyectoDTO> findOne(Long id) {
        log.debug("Request to get ProductoProyecto : {}", id);
        return productoProyectoRepository.findById(id)
            .map(productoProyectoMapper::toDto);
    }

    /**
     * Delete the productoProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoProyecto : {}", id);
        productoProyectoRepository.deleteById(id);
    }
}
