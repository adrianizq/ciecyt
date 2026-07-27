package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ProyectoFaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProyectoFase} and its DTO {@link ProyectoFaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {FasesMapper.class, ProyectoMapper.class})
public interface ProyectoFaseMapper extends EntityMapper<ProyectoFaseDTO, ProyectoFase> {

    @Mapping(source = "proyectoFaseFases.id", target = "proyectoFaseFasesId")
    @Mapping(source = "proyectoFaseFases.fase", target = "proyectoFaseFasesFase")
    @Mapping(source = "proyectoFaseProyecto.id", target = "proyectoFaseProyectoId")
    @Mapping(source = "proyectoFaseProyecto.titulo", target = "proyectoFaseProyectoTitulo")
    ProyectoFaseDTO toDto(ProyectoFase proyectoFase);

    @Mapping(source = "proyectoFaseFasesId", target = "proyectoFaseFases")
    @Mapping(source = "proyectoFaseProyectoId", target = "proyectoFaseProyecto")
    ProyectoFase toEntity(ProyectoFaseDTO proyectoFaseDTO);

    default ProyectoFase fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProyectoFase proyectoFase = new ProyectoFase();
        proyectoFase.setId(id);
        return proyectoFase;
    }
}
