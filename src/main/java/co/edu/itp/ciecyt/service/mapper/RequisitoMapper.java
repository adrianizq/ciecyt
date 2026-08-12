package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.RequisitoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requisito} and its DTO {@link RequisitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModalidadMapper.class})
public interface RequisitoMapper extends EntityMapper<RequisitoDTO, Requisito> {

    @Mapping(source = "requisitoModalidad.id", target = "requisitoModalidadId")
    @Mapping(source = "requisitoModalidad.modalidad", target = "requisitoModalidadModalidad")
    RequisitoDTO toDto(Requisito requisito);

    @Mapping(source = "requisitoModalidadId", target = "requisitoModalidad")
    Requisito toEntity(RequisitoDTO requisitoDTO);

    default Requisito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Requisito requisito = new Requisito();
        requisito.setId(id);
        return requisito;
    }
}
