package com.itp.ciecyt.repository;

import com.itp.ciecyt.domain.ProductoProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoProyectoRepository extends JpaRepository<ProductoProyecto, Long> {

}
