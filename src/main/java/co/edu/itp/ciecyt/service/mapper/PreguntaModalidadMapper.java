package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PreguntaModalidad} and its DTO {@link PreguntaModalidadDTO}.
 */
@Mapper(componentModel = "spring", uses = {PreguntaMapper.class, ModalidadMapper.class})
public interface PreguntaModalidadMapper extends EntityMapper<PreguntaModalidadDTO, PreguntaModalidad> {

    @Mapping(source = "pregunta.id", target = "preguntaId")
    @Mapping(source = "pregunta.pregunta", target = "preguntaPregunta")
    @Mapping(source = "modalidad2.id", target = "modalidad2Id")
    @Mapping(source = "modalidad2.modalidad", target = "modalidad2Modalidad")
    PreguntaModalidadDTO toDto(PreguntaModalidad preguntaModalidad);

    @Mapping(source = "preguntaId", target = "pregunta")
    @Mapping(source = "modalidad2Id", target = "modalidad2")
    PreguntaModalidad toEntity(PreguntaModalidadDTO preguntaModalidadDTO);

    default PreguntaModalidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreguntaModalidad preguntaModalidad = new PreguntaModalidad();
        preguntaModalidad.setId(id);
        return preguntaModalidad;
    }
}
