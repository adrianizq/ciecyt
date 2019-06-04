package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.ResultadosEsperados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultadosEsperados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadosEsperadosRepository extends JpaRepository<ResultadosEsperados, Long> {

}
