package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.PreguntaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pregunta} and its DTO {@link PreguntaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoPreguntaMapper.class, FasesMapper.class, CicloMapper.class})
public interface PreguntaMapper extends EntityMapper<PreguntaDTO, Pregunta> {

    @Mapping(source = "preguntaTipoPregunta.id", target = "preguntaTipoPreguntaId")
    @Mapping(source = "preguntaTipoPregunta.tipoPregunta", target = "preguntaTipoPreguntaTipoPregunta")
    @Mapping(source = "preguntaFase.id", target = "preguntaFaseId")
    @Mapping(source = "preguntaFase.fase", target = "preguntaFaseFase")
    @Mapping(source = "preguntaCiclo.id", target = "preguntaCicloId")
    @Mapping(source = "preguntaCiclo.ciclo", target = "preguntaCicloCiclo")
    PreguntaDTO toDto(Pregunta pregunta);

    @Mapping(source = "preguntaTipoPreguntaId", target = "preguntaTipoPregunta")
    @Mapping(source = "preguntaFaseId", target = "preguntaFase")
    @Mapping(source = "preguntaCicloId", target = "preguntaCiclo")
    Pregunta toEntity(PreguntaDTO preguntaDTO);

    default Pregunta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pregunta pregunta = new Pregunta();
        pregunta.setId(id);
        return pregunta;
    }
}
