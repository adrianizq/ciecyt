package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Authority;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
   //Authority findByName(String authority);
}
