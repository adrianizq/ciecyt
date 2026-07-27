package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElementoMapperTest {

    private ElementoMapper elementoMapper;

    @BeforeEach
    public void setUp() {
        elementoMapper = new ElementoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(elementoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(elementoMapper.fromId(null)).isNull();
    }
}
