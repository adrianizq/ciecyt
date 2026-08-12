package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequisitoProyecto} and its DTO {@link RequisitoProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = {RequisitoMapper.class, ProyectoMapper.class})
public interface RequisitoProyectoMapper extends EntityMapper<RequisitoProyectoDTO, RequisitoProyecto> {

    @Mapping(source = "requisitoProyectoRequisito.id", target = "requisitoProyectoRequisitoId")
    @Mapping(source = "requisitoProyectoRequisito.nombre", target = "requisitoProyectoRequisitoNombre")
    @Mapping(source = "requisitoProyectoRequisito.codigo", target = "requisitoProyectoRequisitoCodigo")
    @Mapping(source = "requisitoProyectoRequisito.obligatorio", target = "requisitoProyectoRequisitoObligatorio")
    @Mapping(source = "requisitoProyectoRequisito.tipo", target = "requisitoProyectoRequisitoTipo")
    @Mapping(source = "requisitoProyectoRequisito.requisitoEstado", target = "requisitoProyectoRequisitoEstado")
    @Mapping(source = "requisitoProyectoProyecto.id", target = "requisitoProyectoProyectoId")
    @Mapping(source = "requisitoProyectoProyecto.titulo", target = "requisitoProyectoProyectoTitulo")
    RequisitoProyectoDTO toDto(RequisitoProyecto requisitoProyecto);

    @Mapping(source = "requisitoProyectoRequisitoId", target = "requisitoProyectoRequisito")
    @Mapping(source = "requisitoProyectoProyectoId", target = "requisitoProyectoProyecto")
    RequisitoProyecto toEntity(RequisitoProyectoDTO requisitoProyectoDTO);

    default RequisitoProyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequisitoProyecto requisitoProyecto = new RequisitoProyecto();
        requisitoProyecto.setId(id);
        return requisitoProyecto;
    }
}
