package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ImpactosEsperadosMapperTest {

    private ImpactosEsperadosMapper impactosEsperadosMapper;

    @BeforeEach
    public void setUp() {
        impactosEsperadosMapper = new ImpactosEsperadosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(impactosEsperadosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(impactosEsperadosMapper.fromId(null)).isNull();
    }
}
