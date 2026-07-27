package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegranteProyectoMapperTest {

    private IntegranteProyectoMapper integranteProyectoMapper;

    @BeforeEach
    public void setUp() {
        integranteProyectoMapper = new IntegranteProyectoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(integranteProyectoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(integranteProyectoMapper.fromId(null)).isNull();
    }
}
