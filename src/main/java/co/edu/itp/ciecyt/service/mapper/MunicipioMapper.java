package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Municipio} and its DTO {@link MunicipioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {



    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
