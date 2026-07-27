package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ProyectoRespuestasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProyectoRespuestas} and its DTO {@link ProyectoRespuestasDTO}.
 */
@Mapper(componentModel = "spring", uses = {PreguntaMapper.class, ProyectoMapper.class})
public interface ProyectoRespuestasMapper extends EntityMapper<ProyectoRespuestasDTO, ProyectoRespuestas> {

    @Mapping(source = "proyectoRespuestasPregunta.id", target = "proyectoRespuestasPreguntaId")
    @Mapping(source = "proyectoRespuestasPregunta.pregunta", target = "proyectoRespuestasPreguntaPregunta")
    @Mapping(source = "proyectoRespuestasProyecto.id", target = "proyectoRespuestasProyectoId")
    @Mapping(source = "proyectoRespuestasProyecto.titulo", target = "proyectoRespuestasProyectoTitulo")
    ProyectoRespuestasDTO toDto(ProyectoRespuestas proyectoRespuestas);

    @Mapping(source = "proyectoRespuestasPreguntaId", target = "proyectoRespuestasPregunta")
    @Mapping(source = "proyectoRespuestasProyectoId", target = "proyectoRespuestasProyecto")
    ProyectoRespuestas toEntity(ProyectoRespuestasDTO proyectoRespuestasDTO);

    default ProyectoRespuestas fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProyectoRespuestas proyectoRespuestas = new ProyectoRespuestas();
        proyectoRespuestas.setId(id);
        return proyectoRespuestas;
    }
}
