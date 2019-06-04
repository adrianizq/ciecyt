package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Solicitud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Solicitud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

}
