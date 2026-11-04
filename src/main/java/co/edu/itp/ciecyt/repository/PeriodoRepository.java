package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Periodo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
    List<Periodo> findAllByOrderByNombreDesc();
}
