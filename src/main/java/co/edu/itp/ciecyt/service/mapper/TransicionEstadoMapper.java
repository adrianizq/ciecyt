package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransicionEstado} and its DTO {@link TransicionEstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModalidadMapper.class})
public interface TransicionEstadoMapper extends EntityMapper<TransicionEstadoDTO, TransicionEstado> {

    @Mapping(source = "transicionEstadoModalidad.id", target = "transicionEstadoModalidadId")
    @Mapping(source = "transicionEstadoModalidad.modalidad", target = "transicionEstadoModalidadModalidad")
    TransicionEstadoDTO toDto(TransicionEstado transicionEstado);

    @Mapping(source = "transicionEstadoModalidadId", target = "transicionEstadoModalidad")
    TransicionEstado toEntity(TransicionEstadoDTO transicionEstadoDTO);

    default TransicionEstado fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransicionEstado transicionEstado = new TransicionEstado();
        transicionEstado.setId(id);
        return transicionEstado;
    }
}
