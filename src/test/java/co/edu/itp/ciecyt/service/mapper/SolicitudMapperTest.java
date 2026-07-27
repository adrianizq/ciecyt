package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SolicitudMapperTest {

    private SolicitudMapper solicitudMapper;

    @BeforeEach
    public void setUp() {
        solicitudMapper = new SolicitudMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(solicitudMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(solicitudMapper.fromId(null)).isNull();
    }
}
