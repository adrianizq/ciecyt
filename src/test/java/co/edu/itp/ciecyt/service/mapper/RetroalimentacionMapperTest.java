package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RetroalimentacionMapperTest {

    private RetroalimentacionMapper retroalimentacionMapper;

    @BeforeEach
    public void setUp() {
        retroalimentacionMapper = new RetroalimentacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(retroalimentacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(retroalimentacionMapper.fromId(null)).isNull();
    }
}
