package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GrupoSemilleroMapperTest {

    private GrupoSemilleroMapper grupoSemilleroMapper;

    @BeforeEach
    public void setUp() {
        grupoSemilleroMapper = new GrupoSemilleroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(grupoSemilleroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grupoSemilleroMapper.fromId(null)).isNull();
    }
}
