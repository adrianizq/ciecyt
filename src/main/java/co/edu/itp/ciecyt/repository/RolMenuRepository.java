package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.RolMenu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RolMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolMenuRepository extends JpaRepository<RolMenu, Long> {
    //public Page<RolMenu>findByAuthNameOrderById(String authority, Pageable p);
    @Query(value="SELECT DISTINCT rm.id, rm.permitir_acceso, rm.permitir_crear, "+
" rm.permitir_editar, rm.permitir_eliminar,rm.auth_name, "+
" rm.rol_menu_menu_id " +
" FROM rol_menu rm  "+
" WHERE ( rm.auth_name=?1 )  "+
" ORDER BY rm.id "
,nativeQuery = true)
public Page<RolMenu> buscarRolMenuAuthority(String authority, Pageable pageable);
}
