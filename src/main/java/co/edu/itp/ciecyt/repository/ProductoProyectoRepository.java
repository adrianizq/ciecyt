package co.edu.itp.ciecyt.repository;
import co.edu.itp.ciecyt.domain.ProductoProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoProyectoRepository extends JpaRepository<ProductoProyecto, Long> {

}
