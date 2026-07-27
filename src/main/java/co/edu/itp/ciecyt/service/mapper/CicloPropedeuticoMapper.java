package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.CicloPropedeuticoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CicloPropedeutico} and its DTO {@link CicloPropedeuticoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModalidadMapper.class, AcuerdoMapper.class})
public interface CicloPropedeuticoMapper extends EntityMapper<CicloPropedeuticoDTO, CicloPropedeutico> {

    @Mapping(source = "modalidad.id", target = "modalidadId")
    @Mapping(source = "cicloPropedeuticoAcuerdo.id", target = "cicloPropedeuticoAcuerdoId")
    @Mapping(source = "cicloPropedeuticoAcuerdo.acuerdo", target = "cicloPropedeuticoAcuerdoAcuerdo")
    CicloPropedeuticoDTO toDto(CicloPropedeutico cicloPropedeutico);

    @Mapping(source = "modalidadId", target = "modalidad")
    @Mapping(source = "cicloPropedeuticoAcuerdoId", target = "cicloPropedeuticoAcuerdo")
    CicloPropedeutico toEntity(CicloPropedeuticoDTO cicloPropedeuticoDTO);

    default CicloPropedeutico fromId(Long id) {
        if (id == null) {
            return null;
        }
        CicloPropedeutico cicloPropedeutico = new CicloPropedeutico();
        cicloPropedeutico.setId(id);
        return cicloPropedeutico;
    }
}
