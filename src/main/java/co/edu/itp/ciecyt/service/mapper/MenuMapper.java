package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.MenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    @Mapping(source = "menuPadre.id", target = "menuPadreId")
    @Mapping(source = "menuPadre.nombre", target = "menuPadreNombre")
    MenuDTO toDto(Menu menu);

    @Mapping(source = "menuPadreId", target = "menuPadre")
    Menu toEntity(MenuDTO menuDTO);

    default Menu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(id);
        return menu;
    }
}
