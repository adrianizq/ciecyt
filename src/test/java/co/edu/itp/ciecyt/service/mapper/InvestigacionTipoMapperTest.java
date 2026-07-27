package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InvestigacionTipoMapperTest {

    private InvestigacionTipoMapper investigacionTipoMapper;

    @BeforeEach
    public void setUp() {
        investigacionTipoMapper = new InvestigacionTipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(investigacionTipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(investigacionTipoMapper.fromId(null)).isNull();
    }
}
