package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Fases;
import co.edu.itp.ciecyt.service.FormatoService;
import co.edu.itp.ciecyt.domain.Formato;
import co.edu.itp.ciecyt.repository.FormatoRepository;
import co.edu.itp.ciecyt.service.dto.FasesDTO;
import co.edu.itp.ciecyt.service.dto.FormatoDTO;
import co.edu.itp.ciecyt.service.mapper.FormatoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Formato}.
 */
@Service
@Transactional
public class FormatoServiceImpl implements FormatoService {

    private final Logger log = LoggerFactory.getLogger(FormatoServiceImpl.class);

    private final FormatoRepository formatoRepository;

    private final FormatoMapper formatoMapper;

    public FormatoServiceImpl(FormatoRepository formatoRepository, FormatoMapper formatoMapper) {
        this.formatoRepository = formatoRepository;
        this.formatoMapper = formatoMapper;
    }

    /**
     * Save a formato.
     *
     * @param formatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormatoDTO save(FormatoDTO formatoDTO) {
        log.debug("Request to save Formato : {}", formatoDTO);
        Formato formato = formatoMapper.toEntity(formatoDTO);
        formato = formatoRepository.save(formato);
        return formatoMapper.toDto(formato);
    }

    /**
     * Get all the formatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Formatoes");
        return formatoRepository.findAll(pageable)
            .map(formatoMapper::toDto);
    }


    /**
     * Get one formato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormatoDTO> findOne(Long id) {
        log.debug("Request to get Formato : {}", id);
        return formatoRepository.findById(id)
            .map(formatoMapper::toDto);
    }

    /**
     * Delete the formato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formato : {}", id);
        formatoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FormatoDTO findByCodigo(String codigo) throws Exception {
        Formato f = formatoRepository.findByCodigo(codigo);
        return formatoMapper.toDto(f);
    }
}
