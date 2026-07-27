package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElementoProyectoMapperTest {

    private ElementoProyectoMapper elementoProyectoMapper;

    @BeforeEach
    public void setUp() {
        elementoProyectoMapper = new ElementoProyectoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(elementoProyectoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(elementoProyectoMapper.fromId(null)).isNull();
    }
}
