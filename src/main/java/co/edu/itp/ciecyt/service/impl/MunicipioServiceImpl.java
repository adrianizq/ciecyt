package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Municipio;
import co.edu.itp.ciecyt.repository.MunicipioRepository;
import co.edu.itp.ciecyt.service.MunicipioService;
import co.edu.itp.ciecyt.service.dto.MunicipioDTO;
import co.edu.itp.ciecyt.service.mapper.MunicipioMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Municipio}.
 */
@Service
@Transactional
public class MunicipioServiceImpl implements MunicipioService {

    private final Logger log = LoggerFactory.getLogger(MunicipioServiceImpl.class);

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    public MunicipioServiceImpl(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }

    @Override
    public MunicipioDTO save(MunicipioDTO municipioDTO) {
        log.debug("Request to save Municipio : {}", municipioDTO);
        Municipio municipio = municipioMapper.toEntity(municipioDTO);
        municipio = municipioRepository.save(municipio);
        return municipioMapper.toDto(municipio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Municipios");
        return municipioRepository.findAll(pageable).map(municipioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioDTO> findAllNoPage() throws Exception {
        List<MunicipioDTO> listDto = new ArrayList<>();
        List<Municipio> list = municipioRepository.findAll();
        for (Municipio menu : list) {
            listDto.add(municipioMapper.toDto(menu));
        }
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioDTO> findByCodigoDaneDepartamento(String codDaneDpto) throws Exception {
        List<MunicipioDTO> listDto = new ArrayList<>();
        List<Municipio> list = municipioRepository.findByCodigoDaneDepartamento(codDaneDpto);
        for (Municipio menu : list) {
            listDto.add(municipioMapper.toDto(menu));
        }
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioDTO> findByDepartamento(String departamento) throws Exception {
        List<MunicipioDTO> listDto = new ArrayList<>();
        List<Municipio> list = municipioRepository.findByDepartamento(departamento);
        for (Municipio menu : list) {
            listDto.add(municipioMapper.toDto(menu));
        }
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MunicipioDTO> findOne(Long id) {
        log.debug("Request to get Municipio : {}", id);
        return municipioRepository.findById(id).map(municipioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Municipio : {}", id);
        municipioRepository.deleteById(id);
    }
}
