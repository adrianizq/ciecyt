package co.edu.itp.ciecyt.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdjuntoRetroalimentacionMapperTest {

    private AdjuntoRetroalimentacionMapper adjuntoRetroalimentacionMapper;

    @BeforeEach
    public void setUp() {
        adjuntoRetroalimentacionMapper = new AdjuntoRetroalimentacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adjuntoRetroalimentacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adjuntoRetroalimentacionMapper.fromId(null)).isNull();
    }
}
