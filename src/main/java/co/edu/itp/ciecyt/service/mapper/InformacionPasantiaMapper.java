package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.InformacionPasantiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InformacionPasantia} and its DTO {@link InformacionPasantiaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProyectoMapper.class})
public interface InformacionPasantiaMapper extends EntityMapper<InformacionPasantiaDTO, InformacionPasantia> {

    @Mapping(source = "informacionPasantiaProyecto.id", target = "informacionPasantiaProyectoId")
    @Mapping(source = "informacionPasantiaProyecto.titulo", target = "informacionPasantiaProyectoTitulo")
   InformacionPasantiaDTO toDto(InformacionPasantia informacionPasantia);


    @Mapping(source = "informacionPasantiaProyectoId", target = "informacionPasantiaProyecto")
    InformacionPasantia toEntity(InformacionPasantiaDTO informacionPasantiaDTO);

    default InformacionPasantia fromId(Long id) {
        if (id == null) {
            return null;
        }
        InformacionPasantia informacionPasantia = new InformacionPasantia();
        informacionPasantia.setId(id);
        return informacionPasantia;
    }
}
