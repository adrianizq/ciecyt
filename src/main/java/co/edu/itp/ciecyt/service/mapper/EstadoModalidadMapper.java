package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.EstadoModalidadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoModalidad} and its DTO {@link EstadoModalidadDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModalidadMapper.class})
public interface EstadoModalidadMapper extends EntityMapper<EstadoModalidadDTO, EstadoModalidad> {

    @Mapping(source = "estadoModalidadModalidad.id", target = "estadoModalidadModalidadId")
    @Mapping(source = "estadoModalidadModalidad.modalidad", target = "estadoModalidadModalidadModalidad")
    EstadoModalidadDTO toDto(EstadoModalidad estadoModalidad);

    @Mapping(source = "estadoModalidadModalidadId", target = "estadoModalidadModalidad")
    EstadoModalidad toEntity(EstadoModalidadDTO estadoModalidadDTO);

    default EstadoModalidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstadoModalidad estadoModalidad = new EstadoModalidad();
        estadoModalidad.setId(id);
        return estadoModalidad;
    }
}
