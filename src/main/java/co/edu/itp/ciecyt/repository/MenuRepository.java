package co.edu.itp.ciecyt.repository;

import co.edu.itp.ciecyt.domain.Menu;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.lang.Long;

/**
 * Spring Data repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query(value=" select distinct m.id,m.nombre, m.url, m.icono, m.activo, "+
			" m.menu_padre_id, m.es_publico, m.orden "+
			" from menu m "+
			" INNER JOIN menu me ON me.menu_padre_id = m.id "
			,nativeQuery = true)
	public List<Menu> buscarMenusPadre();

	@Query(value=" select distinct m.id,m.nombre, m.url, m.icono, m.activo, "+
			" m.menu_padre_id, m.es_publico, m.orden "+
			" from menu m "+
			" LEFT JOIN menu me ON m.menu_padre_id = me.id "+
			" ORDER BY m.menu_padre_id desc, m.orden asc "
			,nativeQuery = true)
	public List<Menu> getAllMenuSystem();


	@Query(value=" SELECT DISTINCT m.id, m.nombre, m.url, m.icono, m.activo,"+
			"m.menu_padre_id, m.es_publico, m.orden   " +
			" FROM menu m  "+
			" INNER JOIN rol_menu rm ON rm.rol_menu_menu_id = m.id "+
			" INNER JOIN jhi_user_authority jua ON rm.auth_name = jua.authority_name " +
			" WHERE (jua.user_id=?1)  " +
			" ORDER BY m.orden"
			,nativeQuery = true)
	public Page<Menu> buscarMenusUsuario(Long idUser, Pageable pageable);

	@Query(value=" SELECT DISTINCT m.id, m.nombre, m.url, m.icono, m.activo,"+
			"m.menu_padre_id, m.es_publico, m.orden   " +
			" FROM menu m  "+
			" INNER JOIN rol_menu rm ON rm.rol_menu_menu_id = m.id "+
			" INNER JOIN jhi_user_authority jua ON rm.auth_name = jua.authority_name " +
			" WHERE (jua.user_id=?1)  " +
			" ORDER BY m.orden"
			,nativeQuery = true)
	public List<Menu> buscarMenusUsuarioNoPage(Long idUser);
   // public List<Menu> findByRolOrderByOrden(String rol);
    public List<Menu> findByRolContainingOrderByOrden(String rol);

    public List <Menu> findAll();

}
