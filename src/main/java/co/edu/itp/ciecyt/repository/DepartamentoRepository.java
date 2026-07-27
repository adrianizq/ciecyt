package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Departamento;

import co.edu.itp.ciecyt.domain.Municipio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Departamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    public List<Departamento> findAll();
}
