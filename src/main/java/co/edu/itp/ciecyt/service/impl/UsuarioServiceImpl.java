package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.UsuarioService;
import co.edu.itp.ciecyt.domain.Usuario;
import co.edu.itp.ciecyt.repository.UsuarioRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.service.dto.UsuarioDTO;
import co.edu.itp.ciecyt.service.mapper.UsuarioMapper;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Usuario}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final UserRepository userRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, UserRepository userRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        // Usuario comparte la clave primaria con User (@MapsId): Hibernate solo acepta la
        // entidad User viva del contexto, no la instancia reconstruida por el mapper.
        if (usuario.getUser() == null || usuario.getUser().getId() == null) {
            throw new BadRequestAlertException("No se indico el usuario propietario del registro", "usuario", "userrequired");
        }
        usuario.setUser(
            userRepository.findById(usuario.getUser().getId())
                .orElseThrow(() -> new BadRequestAlertException("El usuario indicado no existe", "usuario", "usernotfound"))
        );
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable)
            .map(usuarioMapper::toDto);
    }


    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id)
            .map(usuarioMapper::toDto);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
