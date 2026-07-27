package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AcuerdoMapperTest {

    private AcuerdoMapper acuerdoMapper;

    @BeforeEach
    public void setUp() {
        acuerdoMapper = new AcuerdoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(acuerdoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(acuerdoMapper.fromId(null)).isNull();
    }
}
