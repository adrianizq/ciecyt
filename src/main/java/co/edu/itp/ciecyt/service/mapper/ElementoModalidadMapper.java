package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ElementoModalidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ElementoModalidad} and its DTO {@link ElementoModalidadDTO}.
 */
@Mapper(componentModel = "spring", uses = {ElementoMapper.class, ModalidadMapper.class})
public interface ElementoModalidadMapper extends EntityMapper<ElementoModalidadDTO, ElementoModalidad> {

    @Mapping(source = "elemento.id", target = "elementoId")
    @Mapping(source = "elemento.elemento", target = "elementoElemento")
    @Mapping(source = "modalidad.id", target = "modalidadId")
    @Mapping(source = "modalidad.modalidad", target = "modalidadModalidad")
    ElementoModalidadDTO toDto(ElementoModalidad elementoModalidad);

    @Mapping(source = "elementoId", target = "elemento")
    @Mapping(source = "modalidadId", target = "modalidad")
    ElementoModalidad toEntity(ElementoModalidadDTO elementoModalidadDTO);

    default ElementoModalidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        ElementoModalidad elementoModalidad = new ElementoModalidad();
        elementoModalidad.setId(id);
        return elementoModalidad;
    }
}
