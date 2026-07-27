package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FichaTecnicaMapperTest {

    private FichaTecnicaMapper fichaTecnicaMapper;

    @BeforeEach
    public void setUp() {
        fichaTecnicaMapper = new FichaTecnicaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fichaTecnicaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fichaTecnicaMapper.fromId(null)).isNull();
    }
}
