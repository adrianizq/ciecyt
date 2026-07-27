package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ModalidadMapperTest {

    private ModalidadMapper modalidadMapper;

    @BeforeEach
    public void setUp() {
        modalidadMapper = new ModalidadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(modalidadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(modalidadMapper.fromId(null)).isNull();
    }
}
