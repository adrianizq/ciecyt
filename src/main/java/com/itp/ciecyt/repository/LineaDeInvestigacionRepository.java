package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.LineaDeInvestigacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LineaDeInvestigacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineaDeInvestigacionRepository extends JpaRepository<LineaDeInvestigacion, Long> {

}
