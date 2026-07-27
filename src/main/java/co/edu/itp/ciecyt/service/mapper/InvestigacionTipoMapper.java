package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvestigacionTipo} and its DTO {@link InvestigacionTipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvestigacionTipoMapper extends EntityMapper<InvestigacionTipoDTO, InvestigacionTipo> {



    default InvestigacionTipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvestigacionTipo investigacionTipo = new InvestigacionTipo();
        investigacionTipo.setId(id);
        return investigacionTipo;
    }
}
