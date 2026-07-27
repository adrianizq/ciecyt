package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.UserInfo;

import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findAll(Specification<UserInfo> specification);
    //List<UserInfo> findAll(Specification<UserInfo> specification, Pageable page);
    Page<UserInfoDTO> findAll(Specification<UserInfo> specification, Pageable page);
}
