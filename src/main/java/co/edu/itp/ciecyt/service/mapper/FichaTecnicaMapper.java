package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.FichaTecnicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FichaTecnica} and its DTO {@link FichaTecnicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FichaTecnicaMapper extends EntityMapper<FichaTecnicaDTO, FichaTecnica> {

    @Mapping(source = "fichaTecnicaUser.id", target = "fichaTecnicaUserId")
    @Mapping(source = "fichaTecnicaUser.login", target = "fichaTecnicaUserLogin")
    FichaTecnicaDTO toDto(FichaTecnica fichaTecnica);

    @Mapping(source = "fichaTecnicaUserId", target = "fichaTecnicaUser")
    FichaTecnica toEntity(FichaTecnicaDTO fichaTecnicaDTO);

    default FichaTecnica fromId(Long id) {
        if (id == null) {
            return null;
        }
        FichaTecnica fichaTecnica = new FichaTecnica();
        fichaTecnica.setId(id);
        return fichaTecnica;
    }
}
