package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.CronogramaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cronograma} and its DTO {@link CronogramaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class})
public interface CronogramaMapper extends EntityMapper<CronogramaDTO, Cronograma> {

    @Mapping(source = "cronogramaProyecto.id", target = "cronogramaProyectoId")
    @Mapping(source = "cronogramaProyecto.titulo", target = "cronogramaProyectoTitulo")
    CronogramaDTO toDto(Cronograma cronograma);

    @Mapping(source = "cronogramaProyectoId", target = "cronogramaProyecto")
    Cronograma toEntity(CronogramaDTO cronogramaDTO);

    default Cronograma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cronograma cronograma = new Cronograma();
        cronograma.setId(id);
        return cronograma;
    }
}
