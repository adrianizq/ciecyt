package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoHistorialEstadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProyectoHistorialEstado} and its DTO {@link ProyectoHistorialEstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProyectoHistorialEstadoMapper extends EntityMapper<ProyectoHistorialEstadoDTO, ProyectoHistorialEstado> {

    @Mapping(source = "proyecto.id", target = "proyectoId")
    @Mapping(source = "proyecto.titulo", target = "proyectoTitulo")
    ProyectoHistorialEstadoDTO toDto(ProyectoHistorialEstado proyectoHistorialEstado);

    @Mapping(source = "proyectoId", target = "proyecto")
    ProyectoHistorialEstado toEntity(ProyectoHistorialEstadoDTO proyectoHistorialEstadoDTO);

    default ProyectoHistorialEstado fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProyectoHistorialEstado proyectoHistorialEstado = new ProyectoHistorialEstado();
        proyectoHistorialEstado.setId(id);
        return proyectoHistorialEstado;
    }

    default Proyecto proyectoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setId(id);
        return proyecto;
    }

    default String enumEstadoToString(EnumEstadoProyecto estado) {
        return estado == null ? null : estado.name();
    }

    default EnumEstadoProyecto stringToEnumEstado(String estado) {
        return estado == null ? null : EnumEstadoProyecto.valueOf(estado);
    }
}
