package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytFasesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CronogramaCiecytFases} and its DTO {@link CronogramaCiecytFasesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CronogramaCiecytMapper.class, FasesMapper.class})
public interface CronogramaCiecytFasesMapper extends EntityMapper<CronogramaCiecytFasesDTO, CronogramaCiecytFases> {

    @Mapping(source = "cronogramaCiecytFasesCronogramaCiecyt.id", target = "cronogramaCiecytFasesCronogramaCiecytId")
    @Mapping(source = "cronogramaCiecytFasesCronogramaCiecyt.tituloCronograma", target = "cronogramaCiecytFasesCronogramaCiecytTituloCronograma")
    @Mapping(source = "cronogramaCiecytFasesFases.id", target = "cronogramaCiecytFasesFasesId")
    @Mapping(source = "cronogramaCiecytFasesFases.fase", target = "cronogramaCiecytFasesFasesFase")
    CronogramaCiecytFasesDTO toDto(CronogramaCiecytFases cronogramaCiecytFases);

    @Mapping(source = "cronogramaCiecytFasesCronogramaCiecytId", target = "cronogramaCiecytFasesCronogramaCiecyt")
    @Mapping(source = "cronogramaCiecytFasesFasesId", target = "cronogramaCiecytFasesFases")
    CronogramaCiecytFases toEntity(CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO);

    default CronogramaCiecytFases fromId(Long id) {
        if (id == null) {
            return null;
        }
        CronogramaCiecytFases cronogramaCiecytFases = new CronogramaCiecytFases();
        cronogramaCiecytFases.setId(id);
        return cronogramaCiecytFases;
    }
}
