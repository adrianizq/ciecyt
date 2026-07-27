package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Municipio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Municipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    public List<Municipio> findAll();
    public List<Municipio> findByCodigoDaneDepartamento(String codDaneDpto);
    public List<Municipio> findByDepartamento(String departamento);
}
