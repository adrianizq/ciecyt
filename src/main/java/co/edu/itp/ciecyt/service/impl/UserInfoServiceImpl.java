package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.config.ApplicationProperties;
import co.edu.itp.ciecyt.service.UserInfoService;
import co.edu.itp.ciecyt.domain.UserInfo;
import co.edu.itp.ciecyt.repository.UserInfoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import co.edu.itp.ciecyt.service.mapper.UserInfoMapper;
import co.edu.itp.ciecyt.service.util.FileUtils;
import co.edu.itp.ciecyt.service.util.MimeTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing {@link UserInfo}.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final UserRepository userRepository;
    private final ApplicationProperties appProperties;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper,
                               UserRepository userRepository, ApplicationProperties appProperties) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
        this.userRepository = userRepository;
        this.appProperties = appProperties;
    }

    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        Long userId = userInfoDTO.getUserId();
        userRepository.findById(userId).ifPresent(userInfo::user);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    /*@Override
    public List<UserInfoDTO> findAll() {
        return null;
    }*/

   /* @Override
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findAll() {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll().stream()
            .map(userInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }*/

    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable)
            .map(userInfoMapper::toDto);
    }

    @Override
    public Page<UserInfoDTO> findAll() {
        return null;
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findById(id)
            .map(userInfoMapper::toDto);
    }

    /*
     @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findById(id)
            .map(userInfoMapper::toDto);
    }

     */



    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
    }



    /**
     * Guarda un arhivo de imagen para el usuario.
     * Este nombre de archivo se guardará en el campo imageUrl del usuario
     *
     * @param dto the organization dto
     * @param file the byte file image upload
     * @param contentType the contenttype file image
     * @return the file upload name.
     *
     */
    /* Asi esta en dudo
    public String attachFile(UserInfoDTO dto, byte[] file, String contentType){
        String fileName = null;
        //Guarda el archivo en el directorio asignado

        try {
            Path rootDir = Paths.get( appProperties.getUpload().getRoot().getDir());

            Path userDir = rootDir.resolve( appProperties.getUpload().getUser().getDir());

            log.debug("Root upload dir: {}, usersDir: {}, mimetype: {}"
                ,rootDir, userDir, contentType);

            String ext = MimeTypes.getDefaultExt(contentType);
            String nameFile = FileUtils.buildFileName( dto.getId(), ext );

            //FileUtils.writeByteArrayToFile(announcementDir.resolve(nameFile), file );
            Files.write(userDir.resolve(nameFile), file, StandardOpenOption.CREATE);
            //Asigna el nombre el archivo si se pudo crear el archivo cargado
            fileName = nameFile;

        } catch (Exception e) {
            log.error("Error attah file", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return fileName;
    }
    */

}
