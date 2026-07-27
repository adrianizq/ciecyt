package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Modalidad} and its DTO {@link ModalidadDTO}.
 */
@Mapper(componentModel = "spring", uses = {AcuerdoMapper.class})
public interface ModalidadMapper extends EntityMapper<ModalidadDTO, Modalidad> {

    @Mapping(source = "modalidadAcuerdo.id", target = "modalidadAcuerdoId")
    @Mapping(source = "modalidadAcuerdo.acuerdo", target = "modalidadAcuerdoAcuerdo")
    ModalidadDTO toDto(Modalidad modalidad);

    @Mapping(target = "modalidadCicloPropedeuticos", ignore = true)
    @Mapping(target = "removeModalidadCicloPropedeutico", ignore = true)
    @Mapping(source = "modalidadAcuerdoId", target = "modalidadAcuerdo")
    Modalidad toEntity(ModalidadDTO modalidadDTO);

    default Modalidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modalidad modalidad = new Modalidad();
        modalidad.setId(id);
        return modalidad;
    }
}
