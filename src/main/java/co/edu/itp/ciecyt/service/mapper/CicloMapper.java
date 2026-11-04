package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.Ciclo;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.service.dto.CicloDTO;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Ciclo} and its DTO {@link CicloDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CicloMapper extends EntityMapper<CicloDTO, Ciclo> {

    @Mapping(source = "modalidades", target = "modalidadIds")
    @Mapping(source = "modalidades", target = "modalidadNombres")
    CicloDTO toDto(Ciclo ciclo);

    default Ciclo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ciclo ciclo = new Ciclo();
        ciclo.setId(id);
        return ciclo;
    }

    default Set<Long> modalidadesToIds(Set<Modalidad> modalidades) {
        if (modalidades == null) {
            return Collections.emptySet();
        }
        return modalidades.stream().map(Modalidad::getId).collect(Collectors.toSet());
    }

    default Set<String> modalidadesToNombres(Set<Modalidad> modalidades) {
        if (modalidades == null) {
            return Collections.emptySet();
        }
        return modalidades.stream().map(Modalidad::getModalidad).collect(Collectors.toSet());
    }
}
