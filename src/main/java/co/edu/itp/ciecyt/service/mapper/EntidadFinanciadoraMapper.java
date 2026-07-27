package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.EntidadFinanciadoraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntidadFinanciadora} and its DTO {@link EntidadFinanciadoraDTO}.
 */
@Mapper(componentModel = "spring", uses = {EntidadMapper.class, ProyectoMapper.class})
public interface EntidadFinanciadoraMapper extends EntityMapper<EntidadFinanciadoraDTO, EntidadFinanciadora> {

    @Mapping(source = "entidadFinanciadoraEntidad.id", target = "entidadFinanciadoraEntidadId")
    @Mapping(source = "entidadFinanciadoraEntidad.entidad", target = "entidadFinanciadoraEntidadEntidad")
    @Mapping(source = "entidadFinanciadoraProyecto.id", target = "entidadFinanciadoraProyectoId")
    @Mapping(source = "entidadFinanciadoraProyecto.titulo", target = "entidadFinanciadoraProyectoTitulo")
    EntidadFinanciadoraDTO toDto(EntidadFinanciadora entidadFinanciadora);

    @Mapping(source = "entidadFinanciadoraEntidadId", target = "entidadFinanciadoraEntidad")
    @Mapping(source = "entidadFinanciadoraProyectoId", target = "entidadFinanciadoraProyecto")
    EntidadFinanciadora toEntity(EntidadFinanciadoraDTO entidadFinanciadoraDTO);

    default EntidadFinanciadora fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntidadFinanciadora entidadFinanciadora = new EntidadFinanciadora();
        entidadFinanciadora.setId(id);
        return entidadFinanciadora;
    }
}
