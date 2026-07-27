package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.CategorizacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorizacion} and its DTO {@link CategorizacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class})
public interface CategorizacionMapper extends EntityMapper<CategorizacionDTO, Categorizacion> {

    @Mapping(source = "categorizacionProyecto.id", target = "categorizacionProyectoId")
    @Mapping(source = "categorizacionProyecto.titulo", target = "categorizacionProyectoTitulo")
    CategorizacionDTO toDto(Categorizacion categorizacion);

    @Mapping(source = "categorizacionProyectoId", target = "categorizacionProyecto")
    Categorizacion toEntity(CategorizacionDTO categorizacionDTO);

    default Categorizacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categorizacion categorizacion = new Categorizacion();
        categorizacion.setId(id);
        return categorizacion;
    }
}
