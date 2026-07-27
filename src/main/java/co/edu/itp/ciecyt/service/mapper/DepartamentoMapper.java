package co.edu.itp.ciecyt.service.mapper;


import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.DepartamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Departamento} and its DTO {@link DepartamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartamentoMapper extends EntityMapper<DepartamentoDTO, Departamento> {



    default Departamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Departamento departamento = new Departamento();
        departamento.setId(id);
        return departamento;
    }
}
