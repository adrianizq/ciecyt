package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Categorizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Categorizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorizacionRepository extends JpaRepository<Categorizacion, Long> {

}
