package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProyectoRespuestasMapperTest {

    private ProyectoRespuestasMapper proyectoRespuestasMapper;

    @BeforeEach
    public void setUp() {
        proyectoRespuestasMapper = new ProyectoRespuestasMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(proyectoRespuestasMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(proyectoRespuestasMapper.fromId(null)).isNull();
    }
}
