package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.RetroalimentacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Retroalimentacion} and its DTO {@link RetroalimentacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoFaseMapper.class, UserMapper.class})
public interface RetroalimentacionMapper extends EntityMapper<RetroalimentacionDTO, Retroalimentacion> {

    @Mapping(source = "retroalimentacionProyectoFase.id", target = "retroalimentacionProyectoFaseId")
    @Mapping(source = "retroalimentacionProyectoFase.titulo", target = "retroalimentacionProyectoFaseTitulo")
    @Mapping(source = "retroalimentacionUser.id", target = "retroalimentacionUserId")
    @Mapping(source = "retroalimentacionUser.login", target = "retroalimentacionUserLogin")
    RetroalimentacionDTO toDto(Retroalimentacion retroalimentacion);

    @Mapping(source = "retroalimentacionProyectoFaseId", target = "retroalimentacionProyectoFase")
    @Mapping(source = "retroalimentacionUserId", target = "retroalimentacionUser")
    Retroalimentacion toEntity(RetroalimentacionDTO retroalimentacionDTO);

    default Retroalimentacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Retroalimentacion retroalimentacion = new Retroalimentacion();
        retroalimentacion.setId(id);
        return retroalimentacion;
    }
}
