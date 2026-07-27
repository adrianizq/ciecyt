package co.edu.itp.ciecyt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.itp.ciecyt.domain.Menu;
import co.edu.itp.ciecyt.repository.MenuRepository;
import co.edu.itp.ciecyt.service.MenuService;
import co.edu.itp.ciecyt.service.dto.MenuDTO;
import co.edu.itp.ciecyt.service.mapper.MenuMapper;

/**
 * Service Implementation for managing {@link Menu}.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MenuDTO save(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.toEntity(menuDTO);
        menu = menuRepository.save(menu);
        return menuMapper.toDto(menu);
    }

//solo se usa para el crud y no para llamar a todos los menus porque esta paginado
    @Override
    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        return menuRepository.findAll(pageable).map(menuMapper::toDto);
    }

    //se usa para ser llamado para encontrar todos los menus porque no esta paginado
    //
    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findAllOrderByOrden() {
        log.debug("Request to get all Menus");
        List<MenuDTO> listDTO = new ArrayList<>();

        List <Menu> listMenu = menuRepository.findAll();

        for (Menu m :listMenu ) {
            listDTO.add(menuMapper.toDto(m));
        }
        return  listDTO;
    }




    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findByRolOrderByOrden(String rol) throws Exception {
        log.debug("Request to get all Menus");
        List<MenuDTO> listDTO = new ArrayList<>();

        List <Menu> listMenu = menuRepository.findByRolContainingOrderByOrden(rol);

        for (Menu m :listMenu ) {
            listDTO.add(menuMapper.toDto(m));
        }
        return  listDTO;
    }

    //////// traer de un string varios roles para ser buscados en los menus
    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findByRoles(String rol) throws Exception {
        log.debug("Request to get all Menus");
        List <Menu> listMenu = new ArrayList<>();
        String[] array = new String[0];
        try {
            array = rol.split(",");
        } catch (Exception ex) {
           log.debug("Error dividiendo la cadena " + ex.getMessage());
        }
        for (String s : array)
        {
            List <Menu> lMen = menuRepository.findByRolContainingOrderByOrden(s);
            for (Menu m:lMen ){
                if(!existeMenu(m,listMenu)) {
                    listMenu.add(m);
                }
            }
        }
        List<MenuDTO> listDTO = new ArrayList<>();

        for (Menu m :listMenu ) {
            listDTO.add(menuMapper.toDto(m));
        }
        return  listDTO;
    }

    private boolean existeMenu(Menu lm,  List <Menu>  lista) {
        boolean existe=false;
        for (Menu menu :lista ) {
           if (lm.getId()==menu.getId()){
               existe=true;
               break;
           }
        }
        return existe;
    }

    /**
     * Get one menu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MenuDTO> findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findById(id).map(menuMapper::toDto);
    }

    /**
     * Delete the menu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAllFathers() throws Exception {
        log.debug("Request to get all Menus ");
        List <Menu> lista_padres= menuRepository.buscarMenusPadre();
        List <Menu> lista_hijos=null;
        //return menuRepository.buscarMenusPadre();

        List <Menu> lm = new ArrayList<>();
        for (Menu mp :lista_padres){
            //agregar a cada item de lista menu primero el padre
            //despues cada hijo

            if (mp.getMenuPadre()!=null){
                //lista_hijos = menuRepository.buscarMenusHijosDe(mp.getId());

            }
        }
        return lista_padres;
    }


    /**
     * Get all the menus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MenuDTO> buscarAllByUser(Long userId, Pageable pageable) throws Exception {
    	log.debug("Consulta menus paginados para usuario: " + userId );
       /* Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) {
            log.error("User is not logged in");
            return null;
        } else {
            log.debug("El usuario en MenuService tiene id = " + userService.getUserWithAuthorities().get().getId());
        }*/

       /* return menuUserRepository.buscarMenusUsuario(userService.getUserWithAuthorities().get().getId(), pageable)
                .map(menuMapper::toDto);*/

    	 return menuRepository.buscarMenusUsuario(userId, pageable).map(menuMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> buscarAllByUserNoPage(Long userId) throws Exception {
        /*Optional<User> user = userService.getUserWithAuthorities();
	        if (!user.isPresent()) {
	            log.error("User is not logged in");
	            return null;
	        } else {
	            log.debug("El usuario en MenuService tiene id = " + userService.getUserWithAuthorities().get().getId());
	        }
         */
    	List<MenuDTO> listDto = new ArrayList<>();

    	List<Menu> list = menuRepository.buscarMenusUsuarioNoPage( userId );
    	for (Menu menu : list) {
			listDto.add( menuMapper.toDto(menu));
		}

        return listDto;

    }


    @Override
    @Transactional(readOnly = true)
	public List<MenuDTO> getAllMenuSystem() throws Exception {
    	List<MenuDTO> listAll = new ArrayList<>();

    	List<Menu> list = menuRepository.getAllMenuSystem();
    	for (Menu menu : list) {
    		listAll.add( menuMapper.toDto(menu));
		}

    	List<MenuDTO> listDTO = populateTree(null, listAll );

        return listDTO;
	}

    private List<MenuDTO> populateTree( MenuDTO nodo, List<MenuDTO> listAll ) throws Exception {
		Long idParent = 0l;
		if ( nodo == null){
			idParent = null;
		}
		else{
			idParent = nodo.getId();
		}


		List<MenuDTO> list = findNodosByParent(listAll, idParent);

		for (MenuDTO child : list) {


			//Consulta recursiva
			List<MenuDTO> listHijos =  populateTree(child, listAll);
			child.setChildMenus(listHijos);


		}

		return list;
	}

	private List<MenuDTO> findNodosByParent(List<MenuDTO> listAll, Long idMenu ){
		List<MenuDTO> list = new ArrayList<>();
		if( idMenu == null ){
			for (MenuDTO dto : listAll) {
				if( dto.getMenuPadreId() == null ){
					log.debug("ADD MENU PADRE: "+dto);
					list.add(dto);
				}
			}
		}else{
			for (MenuDTO dto : listAll) {
				if( idMenu.equals( dto.getMenuPadreId())){
					log.debug("ADD MENU: "+dto);
					list.add(dto);
				}
			}
		}

		//remueve los nodos encontrados de la lista original
		listAll.removeAll(list);

		return list;
	}



}
