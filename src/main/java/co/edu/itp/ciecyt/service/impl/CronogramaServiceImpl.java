package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CronogramaService;
import co.edu.itp.ciecyt.domain.Cronograma;
import co.edu.itp.ciecyt.repository.CronogramaRepository;
import co.edu.itp.ciecyt.service.dto.CronogramaDTO;
import co.edu.itp.ciecyt.service.mapper.CronogramaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Cronograma}.
 */
@Service
@Transactional
public class CronogramaServiceImpl implements CronogramaService {

    private final Logger log = LoggerFactory.getLogger(CronogramaServiceImpl.class);

    private final CronogramaRepository cronogramaRepository;

    private final CronogramaMapper cronogramaMapper;

    public CronogramaServiceImpl(CronogramaRepository cronogramaRepository, CronogramaMapper cronogramaMapper) {
        this.cronogramaRepository = cronogramaRepository;
        this.cronogramaMapper = cronogramaMapper;
    }

    /**
     * Save a cronograma.
     *
     * @param cronogramaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CronogramaDTO save(CronogramaDTO cronogramaDTO) {
        log.debug("Request to save Cronograma : {}", cronogramaDTO);
        Cronograma cronograma = cronogramaMapper.toEntity(cronogramaDTO);
        cronograma = cronogramaRepository.save(cronograma);
        return cronogramaMapper.toDto(cronograma);
    }

    /**
     * Get all the cronogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CronogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cronogramas");
        return cronogramaRepository.findAll(pageable)
            .map(cronogramaMapper::toDto);
    }


    /**
     * Get one cronograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CronogramaDTO> findOne(Long id) {
        log.debug("Request to get Cronograma : {}", id);
        return cronogramaRepository.findById(id)
            .map(cronogramaMapper::toDto);
    }

    /**
     * Delete the cronograma by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cronograma : {}", id);
        cronogramaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CronogramaDTO> findByCronogramaProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all Cronograma whit a idProyecto");
        List<CronogramaDTO> listDTO = new ArrayList<>();
        List<Cronograma> list = cronogramaRepository.findByCronogramaProyectoIdOrderByOrdenVista(idProyecto);


        for (Cronograma obj : list) {
            listDTO.add(cronogramaMapper.toDto(obj));
        }
        return listDTO;
    }
}
