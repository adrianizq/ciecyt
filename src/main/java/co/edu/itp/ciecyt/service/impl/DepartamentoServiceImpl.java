package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Municipio;
import co.edu.itp.ciecyt.service.DepartamentoService;
import co.edu.itp.ciecyt.domain.Departamento;
import co.edu.itp.ciecyt.repository.DepartamentoRepository;
import co.edu.itp.ciecyt.service.dto.DepartamentoDTO;
import co.edu.itp.ciecyt.service.dto.MunicipioDTO;
import co.edu.itp.ciecyt.service.mapper.DepartamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Departamento}.
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private final Logger log = LoggerFactory.getLogger(DepartamentoServiceImpl.class);

    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoMapper departamentoMapper;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    @Override
    public DepartamentoDTO save(DepartamentoDTO departamentoDTO) {
        log.debug("Request to save Departamento : {}", departamentoDTO);
        Departamento departamento = departamentoMapper.toEntity(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        return departamentoMapper.toDto(departamento);
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<DepartamentoDTO> findAll() {
        log.debug("Request to get all Departamentos");
        return departamentoRepository.findAll().stream()
            .map(departamentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<DepartamentoDTO> findAll()  {
        List<DepartamentoDTO> listDto = new ArrayList<>();
        List<Departamento> list = departamentoRepository.findAll();
        for (Departamento departamento : list) {
            listDto.add(departamentoMapper.toDto(departamento));
        }
        return listDto;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DepartamentoDTO> findOne(Long id) {
        log.debug("Request to get Departamento : {}", id);
        return departamentoRepository.findById(id)
            .map(departamentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Departamento : {}", id);
        departamentoRepository.deleteById(id);
    }
}
