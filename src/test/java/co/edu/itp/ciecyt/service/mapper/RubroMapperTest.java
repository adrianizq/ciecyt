package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RubroMapperTest {

    private RubroMapper rubroMapper;

    @BeforeEach
    public void setUp() {
        rubroMapper = new RubroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rubroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rubroMapper.fromId(null)).isNull();
    }
}
