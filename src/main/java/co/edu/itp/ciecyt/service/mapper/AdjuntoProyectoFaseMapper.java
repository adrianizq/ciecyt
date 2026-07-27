package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.AdjuntoProyectoFaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdjuntoProyectoFase} and its DTO {@link AdjuntoProyectoFaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class, FasesMapper.class})
public interface AdjuntoProyectoFaseMapper extends EntityMapper<AdjuntoProyectoFaseDTO, AdjuntoProyectoFase> {

    @Mapping(source = "proyectoFaseProyecto.id", target = "proyectoFaseProyectoId")
    @Mapping(source = "proyectoFaseProyecto.titulo", target = "proyectoFaseProyectoTitulo")
    @Mapping(source = "adjuntoProyectoFaseFase.id", target = "adjuntoProyectoFaseFaseId")
    @Mapping(source = "adjuntoProyectoFaseFase.fase", target = "adjuntoProyectoFaseFaseFase")
    @Mapping(target = "archivo", ignore = true)
    @Mapping(source = "archivo", target = "file")
    AdjuntoProyectoFaseDTO toDto(AdjuntoProyectoFase adjuntoProyectoFase);

    @Mapping(source = "proyectoFaseProyectoId", target = "proyectoFaseProyecto")
    @Mapping(source = "adjuntoProyectoFaseFaseId", target = "adjuntoProyectoFaseFase")
    @Mapping(source = "file", target = "archivo")
    AdjuntoProyectoFase toEntity(AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO);

    default AdjuntoProyectoFase fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdjuntoProyectoFase adjuntoProyectoFase = new AdjuntoProyectoFase();
        adjuntoProyectoFase.setId(id);
        return adjuntoProyectoFase;
    }
}
