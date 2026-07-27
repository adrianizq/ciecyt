package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoPreguntaMapperTest {

    private TipoPreguntaMapper tipoPreguntaMapper;

    @BeforeEach
    public void setUp() {
        tipoPreguntaMapper = new TipoPreguntaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoPreguntaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoPreguntaMapper.fromId(null)).isNull();
    }
}
