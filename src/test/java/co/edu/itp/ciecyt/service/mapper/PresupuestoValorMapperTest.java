package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PresupuestoValorMapperTest {

    private PresupuestoValorMapper presupuestoValorMapper;

    @BeforeEach
    public void setUp() {
        presupuestoValorMapper = new PresupuestoValorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(presupuestoValorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(presupuestoValorMapper.fromId(null)).isNull();
    }
}
