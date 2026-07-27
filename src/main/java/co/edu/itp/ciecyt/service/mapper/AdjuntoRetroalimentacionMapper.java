package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.AdjuntoRetroalimentacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdjuntoRetroalimentacion} and its DTO {@link AdjuntoRetroalimentacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {  ProyectoMapper.class, FasesMapper.class })
public interface AdjuntoRetroalimentacionMapper extends EntityMapper<AdjuntoRetroalimentacionDTO, AdjuntoRetroalimentacion> {
    @Mapping(source = "adjuntoRetroalimentacionProyecto.id", target = "adjuntoRetroalimentacionProyectoId")
    @Mapping(source = "adjuntoRetroalimentacionProyecto.titulo", target = "adjuntoRetroalimentacionProyectoTitulo")
    @Mapping(source = "adjuntoRetroalimentacionFase.id", target = "adjuntoRetroalimentacionFaseId")
    @Mapping(source = "adjuntoRetroalimentacionFase.fase", target = "adjuntoRetroalimentacionFaseFase")
    @Mapping(target = "archivo", ignore = true)
    @Mapping(source = "archivo", target = "file")
    AdjuntoRetroalimentacionDTO toDto(AdjuntoRetroalimentacion adjuntoRetroalimentacion);


    @Mapping(source = "adjuntoRetroalimentacionProyectoId", target = "adjuntoRetroalimentacionProyecto")
    @Mapping(source = "adjuntoRetroalimentacionFaseId", target = "adjuntoRetroalimentacionFase")
    @Mapping(source = "file", target = "archivo")
    AdjuntoRetroalimentacion toEntity(AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO);

    default AdjuntoRetroalimentacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdjuntoRetroalimentacion adjuntoRetroalimentacion = new AdjuntoRetroalimentacion();
        adjuntoRetroalimentacion.setId(id);
        return adjuntoRetroalimentacion;
    }
}
