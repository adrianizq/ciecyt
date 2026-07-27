package co.edu.itp.ciecyt.service.mapper;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.service.dto.ProductoProyectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoProyecto} and its DTO {@link ProductoProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, ProyectoMapper.class})
public interface ProductoProyectoMapper extends EntityMapper<ProductoProyectoDTO, ProductoProyecto> {

    @Mapping(source = "productoProyectoProducto.id", target = "productoProyectoProductoId")
    @Mapping(source = "productoProyectoProducto.producto", target = "productoProyectoProductoProducto")
    @Mapping(source = "productoProyectoProyecto.id", target = "productoProyectoProyectoId")
    @Mapping(source = "productoProyectoProyecto.titulo", target = "productoProyectoProyectoTitulo")
    ProductoProyectoDTO toDto(ProductoProyecto productoProyecto);

    @Mapping(source = "productoProyectoProductoId", target = "productoProyectoProducto")
    @Mapping(source = "productoProyectoProyectoId", target = "productoProyectoProyecto")
    ProductoProyecto toEntity(ProductoProyectoDTO productoProyectoDTO);

    default ProductoProyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoProyecto productoProyecto = new ProductoProyecto();
        productoProyecto.setId(id);
        return productoProyecto;
    }
}
