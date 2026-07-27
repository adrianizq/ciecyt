package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductoMapperTest {

    private ProductoMapper productoMapper;

    @BeforeEach
    public void setUp() {
        productoMapper = new ProductoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productoMapper.fromId(null)).isNull();
    }
}
