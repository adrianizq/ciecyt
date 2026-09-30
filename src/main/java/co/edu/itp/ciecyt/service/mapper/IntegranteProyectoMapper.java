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
    @Mapping(source = "integranteProyectoUser.firstName", target = "integranteProyectoUserFirstName")
    @Mapping(source = "integranteProyectoUser.lastName", target = "integranteProyectoUserLastName")
    @Mapping(source = "integranteProyectoExterno.id", target = "integranteProyectoExternoId")
    @Mapping(source = "integranteProyectoExterno", target = "integranteProyectoExternoNombre")
    @Mapping(source = "integranteProyectoProyecto.id", target = "integranteProyectoProyectoId")
    @Mapping(source = "integranteProyectoProyecto.titulo", target = "integranteProyectoProyectoTitulo")
    @Mapping(source = "integranteProyectoRolesModalidad.id", target = "integranteProyectoRolesModalidadId")
    @Mapping(source = "integranteProyectoRolesModalidad.rol", target = "integranteProyectoRolesModalidadRol")
    // No viven en IntegranteProyecto sino en UserInfo (misma llave que User); los llena el servicio.
    @Mapping(target = "integranteProyectoUserNuip", ignore = true)
    @Mapping(target = "integranteProyectoUserCodigoItp", ignore = true)
    IntegranteProyectoDTO toDto(IntegranteProyecto integranteProyecto);

    @Mapping(source = "integranteProyectoUserId", target = "integranteProyectoUser")
    @Mapping(source = "integranteProyectoExternoId", target = "integranteProyectoExterno")
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

    default AsesorExterno asesorExternoFromId(Long id) {
        if (id == null) {
            return null;
        }
        AsesorExterno externo = new AsesorExterno();
        externo.setId(id);
        return externo;
    }

    default String integranteProyectoExternoNombre(AsesorExterno externo) {
        return externo == null ? null : externo.getNombreCompleto();
    }

}
