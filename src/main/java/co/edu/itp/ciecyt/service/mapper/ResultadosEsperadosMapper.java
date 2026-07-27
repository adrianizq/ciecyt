package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultadosEsperados} and its DTO {@link ResultadosEsperadosDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class})
public interface ResultadosEsperadosMapper extends EntityMapper<ResultadosEsperadosDTO, ResultadosEsperados> {

    @Mapping(source = "resultadosEsperadosProyecto.id", target = "resultadosEsperadosProyectoId")
    @Mapping(source = "resultadosEsperadosProyecto.titulo", target = "resultadosEsperadosProyectoTitulo")
    ResultadosEsperadosDTO toDto(ResultadosEsperados resultadosEsperados);

    @Mapping(source = "resultadosEsperadosProyectoId", target = "resultadosEsperadosProyecto")
    ResultadosEsperados toEntity(ResultadosEsperadosDTO resultadosEsperadosDTO);

    default ResultadosEsperados fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResultadosEsperados resultadosEsperados = new ResultadosEsperados();
        resultadosEsperados.setId(id);
        return resultadosEsperados;
    }
}
