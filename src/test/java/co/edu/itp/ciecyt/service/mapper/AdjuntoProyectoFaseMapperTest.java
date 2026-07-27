package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdjuntoProyectoFaseMapperTest {

    private AdjuntoProyectoFaseMapper adjuntoProyectoFaseMapper;

    @BeforeEach
    public void setUp() {
        adjuntoProyectoFaseMapper = new AdjuntoProyectoFaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adjuntoProyectoFaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adjuntoProyectoFaseMapper.fromId(null)).isNull();
    }
}
