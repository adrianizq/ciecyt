package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.Notificacion;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.service.dto.NotificacionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Notificacion} and its DTO {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "proyecto.id", target = "proyectoId")
    @Mapping(source = "proyecto.titulo", target = "proyectoTitulo")
    NotificacionDTO toDto(Notificacion notificacion);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "proyectoId", target = "proyecto")
    Notificacion toEntity(NotificacionDTO notificacionDTO);

    default Notificacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setId(id);
        return notificacion;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Proyecto proyectoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setId(id);
        return proyecto;
    }
}
