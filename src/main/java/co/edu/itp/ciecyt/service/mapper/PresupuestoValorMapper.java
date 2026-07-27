package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.PresupuestoValorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PresupuestoValor} and its DTO {@link PresupuestoValorDTO}.
 */
@Mapper(componentModel = "spring", uses = {RubroMapper.class, ProyectoMapper.class, EntidadMapper.class})
public interface PresupuestoValorMapper extends EntityMapper<PresupuestoValorDTO, PresupuestoValor> {

    @Mapping(source = "presupuestoValorRubro.id", target = "presupuestoValorRubroId")
    @Mapping(source = "presupuestoValorRubro.rubro", target = "presupuestoValorRubroRubro")
    @Mapping(source = "presupuestoValorProyecto.id", target = "presupuestoValorProyectoId")
    @Mapping(source = "presupuestoValorProyecto.titulo", target = "presupuestoValorProyectoTitulo")
    @Mapping(source = "presupuestoValorEntidad.id", target = "presupuestoValorEntidadId")
    @Mapping(source = "presupuestoValorEntidad.entidad", target = "presupuestoValorEntidadEntidad")
    PresupuestoValorDTO toDto(PresupuestoValor presupuestoValor);

    @Mapping(source = "presupuestoValorRubroId", target = "presupuestoValorRubro")
    @Mapping(source = "presupuestoValorProyectoId", target = "presupuestoValorProyecto")
    @Mapping(source = "presupuestoValorEntidadId", target = "presupuestoValorEntidad")
    PresupuestoValor toEntity(PresupuestoValorDTO presupuestoValorDTO);

    default PresupuestoValor fromId(Long id) {
        if (id == null) {
            return null;
        }
        PresupuestoValor presupuestoValor = new PresupuestoValor();
        presupuestoValor.setId(id);
        return presupuestoValor;
    }
}
