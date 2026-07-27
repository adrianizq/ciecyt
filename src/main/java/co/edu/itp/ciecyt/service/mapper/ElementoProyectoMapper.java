package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ElementoProyecto} and its DTO {@link ElementoProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ElementoMapper.class, ProyectoMapper.class})
public interface ElementoProyectoMapper extends EntityMapper<ElementoProyectoDTO, ElementoProyecto> {

    @Mapping(source = "elementoProyectoElemento.id", target = "elementoProyectoElementoId")
    @Mapping(source = "elementoProyectoElemento.elemento", target = "elementoProyectoElementoElemento")
    @Mapping(source = "elementoProyectoProyecto.id", target = "elementoProyectoProyectoId")
    @Mapping(source = "elementoProyectoProyecto.titulo", target = "elementoProyectoProyectoTitulo")
    ElementoProyectoDTO toDto(ElementoProyecto elementoProyecto);

    @Mapping(source = "elementoProyectoElementoId", target = "elementoProyectoElemento")
    @Mapping(source = "elementoProyectoProyectoId", target = "elementoProyectoProyecto")
    ElementoProyecto toEntity(ElementoProyectoDTO elementoProyectoDTO);

    default ElementoProyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ElementoProyecto elementoProyecto = new ElementoProyecto();
        elementoProyecto.setId(id);
        return elementoProyecto;
    }
}
