package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PreguntaMapperTest {

    private PreguntaMapper preguntaMapper;

    @BeforeEach
    public void setUp() {
        preguntaMapper = new PreguntaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(preguntaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(preguntaMapper.fromId(null)).isNull();
    }
}
