package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Festivo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    List<Festivo> findAllByOrderByFechaAsc();

    @Query("select f.fecha from Festivo f where f.activo = true and f.fecha between :desde and :hasta")
    List<LocalDate> findFechasActivasEntre(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
