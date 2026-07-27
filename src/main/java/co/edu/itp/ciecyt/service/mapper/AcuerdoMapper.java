package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.AcuerdoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Acuerdo} and its DTO {@link AcuerdoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AcuerdoMapper extends EntityMapper<AcuerdoDTO, Acuerdo> {



    default Acuerdo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Acuerdo acuerdo = new Acuerdo();
        acuerdo.setId(id);
        return acuerdo;
    }
}
