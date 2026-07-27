package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProyectoFaseMapperTest {

    private ProyectoFaseMapper proyectoFaseMapper;

    @BeforeEach
    public void setUp() {
        proyectoFaseMapper = new ProyectoFaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(proyectoFaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(proyectoFaseMapper.fromId(null)).isNull();
    }
}
