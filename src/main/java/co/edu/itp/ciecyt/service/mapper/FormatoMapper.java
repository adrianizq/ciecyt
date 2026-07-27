package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.FormatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Formato} and its DTO {@link FormatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormatoMapper extends EntityMapper<FormatoDTO, Formato> {



    default Formato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Formato formato = new Formato();
        formato.setId(id);
        return formato;
    }
}
