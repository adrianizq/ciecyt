package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CronogramaCiecyt} and its DTO {@link CronogramaCiecytDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModalidadMapper.class})
public interface CronogramaCiecytMapper extends EntityMapper<CronogramaCiecytDTO, CronogramaCiecyt> {

    @Mapping(source = "cronogramaCiecytModalidad.id", target = "cronogramaCiecytModalidadId")
    @Mapping(source = "cronogramaCiecytModalidad.modalidad", target = "cronogramaCiecytModalidadModalidad")
    CronogramaCiecytDTO toDto(CronogramaCiecyt cronogramaCiecyt);

    @Mapping(source = "cronogramaCiecytModalidadId", target = "cronogramaCiecytModalidad")
    CronogramaCiecyt toEntity(CronogramaCiecytDTO cronogramaCiecytDTO);

    default CronogramaCiecyt fromId(Long id) {
        if (id == null) {
            return null;
        }
        CronogramaCiecyt cronogramaCiecyt = new CronogramaCiecyt();
        cronogramaCiecyt.setId(id);
        return cronogramaCiecyt;
    }
}
