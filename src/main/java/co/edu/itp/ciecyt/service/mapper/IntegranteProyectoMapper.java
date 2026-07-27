package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity {@link IntegranteProyecto} and its DTO {@link IntegranteProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProyectoMapper.class, RolesModalidadMapper.class})
public interface IntegranteProyectoMapper extends EntityMapper<IntegranteProyectoDTO, IntegranteProyecto> {

    @Mapping(source = "integranteProyectoUser.id", target = "integranteProyectoUserId")
    @Mapping(source = "integranteProyectoUser.login", target = "integranteProyectoUserLogin")
    @Mapping(source = "integranteProyectoProyecto.id", target = "integranteProyectoProyectoId")
    @Mapping(source = "integranteProyectoProyecto.titulo", target = "integranteProyectoProyectoTitulo")
    @Mapping(source = "integranteProyectoRolesModalidad.id", target = "integranteProyectoRolesModalidadId")
    @Mapping(source = "integranteProyectoRolesModalidad.rol", target = "integranteProyectoRolesModalidadRol")
    IntegranteProyectoDTO toDto(IntegranteProyecto integranteProyecto);

    @Mapping(source = "integranteProyectoUserId", target = "integranteProyectoUser")
    @Mapping(source = "integranteProyectoProyectoId", target = "integranteProyectoProyecto")
    @Mapping(source = "integranteProyectoRolesModalidadId", target = "integranteProyectoRolesModalidad")
    IntegranteProyecto toEntity(IntegranteProyectoDTO integranteProyectoDTO);

    default IntegranteProyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        IntegranteProyecto integranteProyecto = new IntegranteProyecto();
        integranteProyecto.setId(id);
        return integranteProyecto;
    }

}
