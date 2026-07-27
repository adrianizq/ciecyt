package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FormatoMapperTest {

    private FormatoMapper formatoMapper;

    @BeforeEach
    public void setUp() {
        formatoMapper = new FormatoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(formatoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(formatoMapper.fromId(null)).isNull();
    }
}
