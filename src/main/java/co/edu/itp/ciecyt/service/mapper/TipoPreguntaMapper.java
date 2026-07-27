package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.TipoPreguntaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoPregunta} and its DTO {@link TipoPreguntaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoPreguntaMapper extends EntityMapper<TipoPreguntaDTO, TipoPregunta> {



    default TipoPregunta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoPregunta tipoPregunta = new TipoPregunta();
        tipoPregunta.setId(id);
        return tipoPregunta;
    }
}
