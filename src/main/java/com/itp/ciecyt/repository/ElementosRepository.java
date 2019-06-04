package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.Elementos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Elementos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementosRepository extends JpaRepository<Elementos, Long> {

}
