package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElementoModalidadMapperTest {

    private ElementoModalidadMapper elementoModalidadMapper;

    @BeforeEach
    public void setUp() {
        elementoModalidadMapper = new ElementoModalidadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(elementoModalidadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(elementoModalidadMapper.fromId(null)).isNull();
    }
}
