package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.UserInfo}.
 */
public interface UserInfoService {

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save.
     * @return the persisted entity.
     */
    UserInfoDTO save(UserInfoDTO userInfoDTO);

    /**
     * Get all the userInfos.
     *
     * @return the list of entities.
     */
    //List<UserInfoDTO> findAll();
    Page<UserInfoDTO> findAll(Pageable pageable);
    // Page<UserInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserInfoDTO> findOne(Long id);

    /**
     * Delete the "id" userInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<UserInfoDTO> findAll();

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
   // public String attachFile(UserInfoDTO dto, byte[] file, String contentType);
}
