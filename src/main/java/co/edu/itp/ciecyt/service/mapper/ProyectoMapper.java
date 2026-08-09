package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proyecto} and its DTO {@link ProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = {LineaInvestigacionMapper.class, GrupoSemilleroMapper.class, ModalidadMapper.class, FacultadMapper.class, ProgramaMapper.class,FasesMapper.class, CicloMapper.class })
public interface ProyectoMapper extends EntityMapper<ProyectoDTO, Proyecto> {

    @Mapping(source = "proyectoLineaInvestigacion.id", target = "proyectoLineaInvestigacionId")
    @Mapping(source = "proyectoLineaInvestigacion.linea", target = "proyectoLineaInvestigacionLinea")
    @Mapping(source = "proyectoGrupoSemillero.id", target = "proyectoGrupoSemilleroId")
    @Mapping(source = "proyectoGrupoSemillero.nombre", target = "proyectoGrupoSemilleroNombre")
    @Mapping(source = "proyectoModalidad.id", target = "proyectoModalidadId")
    @Mapping(source = "proyectoModalidad.modalidad", target = "proyectoModalidadModalidad")
    @Mapping(source = "facultad.id", target = "facultadId")
    @Mapping(source = "subLineaLineaInvestigacion.id", target = "subLineaLineaInvestigacionId")
    @Mapping(source = "subLineaLineaInvestigacion.linea", target = "subLineaLineaInvestigacionLinea")
    @Mapping(source = "proyectoPrograma.id", target = "proyectoProgramaId")
    @Mapping(source = "proyectoPrograma.programa", target = "programa")
    @Mapping(source = "proyectoFase.id", target = "proyectoFaseId")
    @Mapping(source = "proyectoFase.fase", target = "fase")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
    @Mapping(source = "proyectoCiclo.id", target = "proyectoCicloId")
    ProyectoDTO toDto(Proyecto proyecto);

    @Named("estadoToString")
    default String estadoToString(EnumEstadoProyecto estado) {
        return estado == null ? null : estado.name();
    }

    @Named("stringToEstado")
    default EnumEstadoProyecto stringToEstado(String estado) {
        return estado == null ? null : EnumEstadoProyecto.valueOf(estado);
    }

    @Mapping(source = "proyectoLineaInvestigacionId", target = "proyectoLineaInvestigacion")
    @Mapping(source = "proyectoGrupoSemilleroId", target = "proyectoGrupoSemillero")
    @Mapping(source = "proyectoModalidadId", target = "proyectoModalidad")
    @Mapping(source = "facultadId", target = "facultad")
    @Mapping(source = "subLineaLineaInvestigacionId", target = "subLineaLineaInvestigacion")
    @Mapping(source = "proyectoProgramaId", target = "proyectoPrograma")
    @Mapping(source = "proyectoFaseId", target = "proyectoFase")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToEstado")
    @Mapping(source = "proyectoCicloId", target = "proyectoCiclo")
    Proyecto toEntity(ProyectoDTO proyectoDTO);

    default Proyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setId(id);
        return proyecto;
    }
}
