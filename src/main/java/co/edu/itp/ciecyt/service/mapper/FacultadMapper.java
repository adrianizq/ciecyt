package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.FacultadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facultad} and its DTO {@link FacultadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacultadMapper extends EntityMapper<FacultadDTO, Facultad> {


    @Mapping(target = "facultadProyectos", ignore = true)
    @Mapping(target = "removeFacultadProyecto", ignore = true)
    Facultad toEntity(FacultadDTO facultadDTO);

    default Facultad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facultad facultad = new Facultad();
        facultad.setId(id);
        return facultad;
    }
}
