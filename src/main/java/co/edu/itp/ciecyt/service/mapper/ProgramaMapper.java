package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ProgramaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Programa} and its DTO {@link ProgramaDTO}.
 */
@Mapper(componentModel = "spring", uses = {FacultadMapper.class})
public interface ProgramaMapper extends EntityMapper<ProgramaDTO, Programa> {

    @Mapping(source = "programaFacultad.id", target = "programaFacultadId")
    @Mapping(source = "programaFacultad.facultad", target = "programaFacultadFacultad")
    ProgramaDTO toDto(Programa programa);

    @Mapping(source = "programaFacultadId", target = "programaFacultad")
    Programa toEntity(ProgramaDTO programaDTO);

    default Programa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Programa programa = new Programa();
        programa.setId(id);
        return programa;
    }
}
