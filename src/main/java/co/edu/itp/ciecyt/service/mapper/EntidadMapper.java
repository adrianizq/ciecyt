package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.EntidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entidad} and its DTO {@link EntidadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntidadMapper extends EntityMapper<EntidadDTO, Entidad> {



    default Entidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entidad entidad = new Entidad();
        entidad.setId(id);
        return entidad;
    }
}
