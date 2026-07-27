package co.edu.itp.ciecyt.service;



import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import io.github.jhipster.service.QueryService;


import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.config.ApplicationProperties;
import co.edu.itp.ciecyt.domain.*; // for static metamodels

import co.edu.itp.ciecyt.repository.UserRepository;
//import co.edu.itp.ciecyt.service.dto.GameCriteria;
import co.edu.itp.ciecyt.service.dto.UserCriteria;
import co.edu.itp.ciecyt.service.dto.UserDTO;
import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import co.edu.itp.ciecyt.service.mapper.UserMapper;
import co.edu.itp.ciecyt.service.util.FileUtils;

/**
 * Service for executing complex queries for {@link UserInfo} entities in the database.
 * The main input is a {@link UserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserDTO} or a {@link Page} of {@link UserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserInfoService userInfoService;

    private final ApplicationProperties appProperties;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper,
                            UserInfoService userInfoService, ApplicationProperties appProperties) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userInfoService = userInfoService;
        this.appProperties = appProperties;
    }

    /**
     * Return a {@link List} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userMapper.usersToUserDTOs(userRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
   // @Transactional(readOnly = true)
   /* public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page)
            .map(userMapper::userToUserDTO).map(user->{
                userInfoService.findOne(user.getId()).ifPresent(user::setUserInfo);
                if( user.getUserInfo() == null ){
                    UserInfoDTO info = new UserInfoDTO();
                    info.setUserId(user.getId());
                    user.setUserInfo( info );
                }else{
                    // TODO Validar el archivo fisico
                    if( user.getImageUrl() != null ){
                        //Construye la URL del usuario
                        String imageUrl = FileUtils.buildURLImage( appProperties.getImagePath(), user.getImageUrl(),
                            appProperties.getUpload().getUser() );
                        user.setImageUrl(imageUrl);
                    }

                }
                return user;
            });
    }
*/


    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    /*
    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.count(specification);
    }
    */


    /**
     * Function to convert {@link UserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), User_.activated));
            }
         /*   if (criteria.getPremium() != null) {
                specification = specification.and(buildSpecification(criteria.getPremium(), User_.premium));
            }

          */
         /*
            if (criteria.getOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrganizationId(),
                    root -> root.join(User_.organization, JoinType.LEFT).get(Organization_.id)));
            }

          */

        }
        return specification;
    }
}
