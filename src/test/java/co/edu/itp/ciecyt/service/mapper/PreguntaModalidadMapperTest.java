package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PreguntaModalidadMapperTest {

    private PreguntaModalidadMapper preguntaModalidadMapper;

    @BeforeEach
    public void setUp() {
        preguntaModalidadMapper = new PreguntaModalidadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(preguntaModalidadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(preguntaModalidadMapper.fromId(null)).isNull();
    }
}
