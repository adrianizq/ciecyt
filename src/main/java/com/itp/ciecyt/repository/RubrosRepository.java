package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Rubros;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rubros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubrosRepository extends JpaRepository<Rubros, Long> {

}
