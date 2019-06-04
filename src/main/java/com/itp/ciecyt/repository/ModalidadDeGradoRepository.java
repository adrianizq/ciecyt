package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.ModalidadDeGrado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModalidadDeGrado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalidadDeGradoRepository extends JpaRepository<ModalidadDeGrado, Long> {

}
