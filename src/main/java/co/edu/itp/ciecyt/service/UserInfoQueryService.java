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

import co.edu.itp.ciecyt.domain.UserInfo;
import co.edu.itp.ciecyt.domain.*; // for static metamodels
import co.edu.itp.ciecyt.repository.UserInfoRepository;
import co.edu.itp.ciecyt.service.dto.UserInfoCriteria;
import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import co.edu.itp.ciecyt.service.mapper.UserInfoMapper;

/**
 * Service for executing complex queries for {@link UserInfo} entities in the database.
 * The main input is a {@link UserInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserInfoDTO} or a {@link Page} of {@link UserInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserInfoQueryService extends QueryService<UserInfo> {

    private final Logger log = LoggerFactory.getLogger(UserInfoQueryService.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoQueryService(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Return a {@link List} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findByCriteria(UserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoMapper.toDto(userInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findByCriteria(UserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.findAll(specification, page)
            .map(userInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
  /* @Transactional(readOnly = true)
    public long countByCriteria(UserInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.count(specification);
    }*/

    /**
     * Function to convert {@link UserInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserInfo> createSpecification(UserInfoCriteria criteria) {
        Specification<UserInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserInfo_.id));
            }
            if (criteria.getNuip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNuip(), UserInfo_.nuip));
            }
            if (criteria.getCodigoItp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoItp(), UserInfo_.codigoItp));
            }
            if (criteria.getGenero() != null) {
                specification = specification.and(buildSpecification(criteria.getGenero(), UserInfo_.genero));
            }
            if (criteria.getCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelular(), UserInfo_.celular));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), UserInfo_.telefono));
            }
            /*if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(UserInfo_.country, JoinType.LEFT).get(Country_.id)));
            }

             */
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserInfo_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
