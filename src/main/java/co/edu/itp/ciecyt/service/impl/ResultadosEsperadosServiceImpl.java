package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.service.ResultadosEsperadosService;
import co.edu.itp.ciecyt.domain.ResultadosEsperados;
import co.edu.itp.ciecyt.repository.ResultadosEsperadosRepository;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.ResultadosEsperadosMapper;
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
 * Service Implementation for managing {@link ResultadosEsperados}.
 */
@Service
@Transactional
public class ResultadosEsperadosServiceImpl implements ResultadosEsperadosService {

    private final Logger log = LoggerFactory.getLogger(ResultadosEsperadosServiceImpl.class);

    private final ResultadosEsperadosRepository resultadosEsperadosRepository;

    private final ResultadosEsperadosMapper resultadosEsperadosMapper;

    public ResultadosEsperadosServiceImpl(ResultadosEsperadosRepository resultadosEsperadosRepository, ResultadosEsperadosMapper resultadosEsperadosMapper) {
        this.resultadosEsperadosRepository = resultadosEsperadosRepository;
        this.resultadosEsperadosMapper = resultadosEsperadosMapper;
    }

    /**
     * Save a resultadosEsperados.
     *
     * @param resultadosEsperadosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResultadosEsperadosDTO save(ResultadosEsperadosDTO resultadosEsperadosDTO) {
        log.debug("Request to save ResultadosEsperados : {}", resultadosEsperadosDTO);
        ResultadosEsperados resultadosEsperados = resultadosEsperadosMapper.toEntity(resultadosEsperadosDTO);
        resultadosEsperados = resultadosEsperadosRepository.save(resultadosEsperados);
        return resultadosEsperadosMapper.toDto(resultadosEsperados);
    }

    /**
     * Get all the resultadosEsperados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResultadosEsperadosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResultadosEsperados");
        return resultadosEsperadosRepository.findAll(pageable)
            .map(resultadosEsperadosMapper::toDto);
    }


    /**
     * Get one resultadosEsperados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResultadosEsperadosDTO> findOne(Long id) {
        log.debug("Request to get ResultadosEsperados : {}", id);
        return resultadosEsperadosRepository.findById(id)
            .map(resultadosEsperadosMapper::toDto);
    }

    /**
     * Delete the resultadosEsperados by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultadosEsperados : {}", id);
        resultadosEsperadosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultadosEsperadosDTO> findByResultadosEsperadosProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all ResultadosEsperados whit a idProyecto");
        List <ResultadosEsperadosDTO> listDTO = new ArrayList<>();
        List <ResultadosEsperados> list = resultadosEsperadosRepository.findByResultadosEsperadosProyectoIdOrderByOrdenVista(idProyecto);
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (ResultadosEsperados resultado : list) {
            listDTO.add( resultadosEsperadosMapper.toDto(resultado));
        }
        return listDTO;

    }
}
