package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImpactosEsperados} and its DTO {@link ImpactosEsperadosDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class})
public interface ImpactosEsperadosMapper extends EntityMapper<ImpactosEsperadosDTO, ImpactosEsperados> {

    @Mapping(source = "impactosEsperadoProyecto.id", target = "impactosEsperadoProyectoId")
    @Mapping(source = "impactosEsperadoProyecto.titulo", target = "impactosEsperadoProyectoTitulo")
    ImpactosEsperadosDTO toDto(ImpactosEsperados impactosEsperados);

    @Mapping(source = "impactosEsperadoProyectoId", target = "impactosEsperadoProyecto")
    ImpactosEsperados toEntity(ImpactosEsperadosDTO impactosEsperadosDTO);

    default ImpactosEsperados fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImpactosEsperados impactosEsperados = new ImpactosEsperados();
        impactosEsperados.setId(id);
        return impactosEsperados;
    }
}
