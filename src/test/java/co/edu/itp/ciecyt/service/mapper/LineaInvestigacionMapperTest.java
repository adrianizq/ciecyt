package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LineaInvestigacionMapperTest {

    private LineaInvestigacionMapper lineaInvestigacionMapper;

    @BeforeEach
    public void setUp() {
        lineaInvestigacionMapper = new LineaInvestigacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lineaInvestigacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lineaInvestigacionMapper.fromId(null)).isNull();
    }
}
