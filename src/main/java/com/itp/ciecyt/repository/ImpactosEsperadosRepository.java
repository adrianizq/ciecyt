package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.ImpactosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImpactosEsperados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpactosEsperadosRepository extends JpaRepository<ImpactosEsperados, Long> {

}
