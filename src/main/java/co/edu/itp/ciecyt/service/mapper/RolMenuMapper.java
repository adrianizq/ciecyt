package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.RolMenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RolMenu} and its DTO {@link RolMenuDTO}.
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class})
public interface RolMenuMapper extends EntityMapper<RolMenuDTO, RolMenu> {

    @Mapping(source = "rolMenuMenu.id", target = "rolMenuMenuId")
    @Mapping(source = "rolMenuMenu.nombre", target = "rolMenuMenuNombre")
    RolMenuDTO toDto(RolMenu rolMenu);

    @Mapping(source = "rolMenuMenuId", target = "rolMenuMenu")
    RolMenu toEntity(RolMenuDTO rolMenuDTO);

    default RolMenu fromId(Long id) {
        if (id == null) {
            return null;
        }
        RolMenu rolMenu = new RolMenu();
        rolMenu.setId(id);
        return rolMenu;
    }
}
