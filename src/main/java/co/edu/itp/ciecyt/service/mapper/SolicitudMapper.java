package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.SolicitudDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Solicitud} and its DTO {@link SolicitudDTO}.
 */
@Mapper(componentModel = "spring", uses = {IntegranteProyectoMapper.class})
public interface SolicitudMapper extends EntityMapper<SolicitudDTO, Solicitud> {

    @Mapping(source = "solicitudIntegranteProyecto.id", target = "solicitudIntegranteProyectoId")
    @Mapping(source = "solicitudIntegranteProyecto.integrante", target = "solicitudIntegranteProyectoIntegrante")
    SolicitudDTO toDto(Solicitud solicitud);

    @Mapping(source = "solicitudIntegranteProyectoId", target = "solicitudIntegranteProyecto")
    Solicitud toEntity(SolicitudDTO solicitudDTO);

    default Solicitud fromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitud solicitud = new Solicitud();
        solicitud.setId(id);
        return solicitud;
    }
}
