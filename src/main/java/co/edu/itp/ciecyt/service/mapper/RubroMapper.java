package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.RubroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rubro} and its DTO {@link RubroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RubroMapper extends EntityMapper<RubroDTO, Rubro> {



    default Rubro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rubro rubro = new Rubro();
        rubro.setId(id);
        return rubro;
    }
}
