package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProyectoMapperTest {

    private ProyectoMapper proyectoMapper;

    @BeforeEach
    public void setUp() {
        proyectoMapper = new ProyectoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(proyectoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(proyectoMapper.fromId(null)).isNull();
    }
}
