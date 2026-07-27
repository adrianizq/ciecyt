package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ElementoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Elemento} and its DTO {@link ElementoDTO}.
 */
@Mapper(componentModel = "spring", uses = {FasesMapper.class})
public interface ElementoMapper extends EntityMapper<ElementoDTO, Elemento> {

    @Mapping(source = "elementoFases.id", target = "elementoFasesId")
    @Mapping(source = "elementoFases.fase", target = "elementoFasesFase")
    ElementoDTO toDto(Elemento elemento);

    @Mapping(source = "elementoFasesId", target = "elementoFases")
    Elemento toEntity(ElementoDTO elementoDTO);

    default Elemento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Elemento elemento = new Elemento();
        elemento.setId(id);
        return elemento;
    }
}
