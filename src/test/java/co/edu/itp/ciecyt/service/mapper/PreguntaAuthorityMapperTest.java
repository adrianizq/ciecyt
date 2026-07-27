package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PreguntaAuthorityMapperTest {

    private PreguntaAuthorityMapper preguntaAuthorityMapper;

    @BeforeEach
    public void setUp() {
        preguntaAuthorityMapper = new PreguntaAuthorityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(preguntaAuthorityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(preguntaAuthorityMapper.fromId(null)).isNull();
    }
}
