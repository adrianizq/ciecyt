package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.GrupoSemilleroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoSemillero} and its DTO {@link GrupoSemilleroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GrupoSemilleroMapper extends EntityMapper<GrupoSemilleroDTO, GrupoSemillero> {



    default GrupoSemillero fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoSemillero grupoSemillero = new GrupoSemillero();
        grupoSemillero.setId(id);
        return grupoSemillero;
    }
}
