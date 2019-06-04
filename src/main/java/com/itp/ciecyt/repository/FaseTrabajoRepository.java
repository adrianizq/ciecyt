package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.FaseTrabajo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FaseTrabajo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaseTrabajoRepository extends JpaRepository<FaseTrabajo, Long> {

}
