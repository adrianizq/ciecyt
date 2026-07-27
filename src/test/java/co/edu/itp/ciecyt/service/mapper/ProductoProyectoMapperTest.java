package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductoProyectoMapperTest {

    private ProductoProyectoMapper productoProyectoMapper;

    @BeforeEach
    public void setUp() {
        productoProyectoMapper = new ProductoProyectoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productoProyectoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productoProyectoMapper.fromId(null)).isNull();
    }
}
