package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.RolMenuService;
import co.edu.itp.ciecyt.domain.RolMenu;
import co.edu.itp.ciecyt.repository.RolMenuRepository;
import co.edu.itp.ciecyt.service.dto.RolMenuDTO;
import co.edu.itp.ciecyt.service.mapper.RolMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RolMenu}.
 */
@Service
@Transactional
public class RolMenuServiceImpl implements RolMenuService {

    private final Logger log = LoggerFactory.getLogger(RolMenuServiceImpl.class);

    private final RolMenuRepository rolMenuRepository;

    private final RolMenuMapper rolMenuMapper;

    public RolMenuServiceImpl(RolMenuRepository rolMenuRepository, RolMenuMapper rolMenuMapper) {
        this.rolMenuRepository = rolMenuRepository;
        this.rolMenuMapper = rolMenuMapper;
    }

    /**
     * Save a rolMenu.
     *
     * @param rolMenuDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RolMenuDTO save(RolMenuDTO rolMenuDTO) {
        log.debug("Request to save RolMenu : {}", rolMenuDTO);
        RolMenu rolMenu = rolMenuMapper.toEntity(rolMenuDTO);
        rolMenu = rolMenuRepository.save(rolMenu);
        return rolMenuMapper.toDto(rolMenu);
    }

    /**
     * Get all the rolMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RolMenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RolMenus");
        return rolMenuRepository.findAll(pageable)
            .map(rolMenuMapper::toDto);
    }


    /**
     * Get one rolMenu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RolMenuDTO> findOne(Long id) {
        log.debug("Request to get RolMenu : {}", id);
        return rolMenuRepository.findById(id)
            .map(rolMenuMapper::toDto);
    }

    /**
     * Delete the rolMenu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RolMenu : {}", id);
        rolMenuRepository.deleteById(id);
    }
/*
    @Override
    public Page<RolMenuDTO> findAllByAuthority(String authority, Pageable pageable) {
        log.debug("Request to get all RolMenus by Authority");
        return rolMenuRepository.findByAuthNameOrderById(authority, pageable)
            .map(rolMenuMapper::toDto);
    }
*/

@Override
public Page<RolMenuDTO> buscarAllByAuthority(String authority, Pageable pageable) {
    log.debug("Request to get all RolMenus by Authority");
    return rolMenuRepository.buscarRolMenuAuthority(authority, pageable)
        .map(rolMenuMapper::toDto);
}
}
